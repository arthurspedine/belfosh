package br.com.spedine.bookshelf.service;

import br.com.spedine.bookshelf.dto.ReviewDTO;
import br.com.spedine.bookshelf.infra.exception.BookAlreadyOnShelf;
import br.com.spedine.bookshelf.infra.exception.BookNotOnUserShelf;
import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.model.Review;
import br.com.spedine.bookshelf.model.User;
import br.com.spedine.bookshelf.model.api.ItemsData;
import br.com.spedine.bookshelf.dto.BookDTO;
import br.com.spedine.bookshelf.model.Author;
import br.com.spedine.bookshelf.model.api.VolumeData;
import br.com.spedine.bookshelf.repository.AuthorRepository;
import br.com.spedine.bookshelf.repository.BookRepository;
import br.com.spedine.bookshelf.repository.ReviewRepository;
import br.com.spedine.bookshelf.repository.UserRepository;
import br.com.spedine.bookshelf.service.api.DataConverter;
import br.com.spedine.bookshelf.service.api.RequestAPI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    DataConverter dataConverter = new DataConverter();

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<BookDTO> getAllBooksFromSearch(String name) {
        ItemsData data = dataConverter.getData(RequestAPI.getJsonData(name), ItemsData.class);
        return data.items().stream()
                .filter(v -> v != null &&
                        v.volumeInfo().imageLinks() != null &&
                        v.volumeInfo().authors() != null &&
                        !v.volumeInfo().authors().isEmpty() &&
                        v.volumeInfo().publishedDate() != null &&
                        v.volumeInfo().summary() != null &&
                        v.volumeInfo().title() != null &&
                        v.volumeInfo().publisher() != null &&
                        v.volumeInfo().totalPages() != null)
                .map(BookDTO::new).toList();
    }

    public BookDTO getBookDataById(String id) {
        VolumeData volume = dataConverter.getData(RequestAPI.getBookById(id), VolumeData.class);
        return new BookDTO(volume);
    }

    public Book addBookIntoDatabase(String id) {
        BookDTO book_data = getBookDataById(id);
        Author author = getAuthorByName(book_data.author());
        if (author == null) {
            author = new Author();
            author.setName(book_data.author());
            saveAuthor(author);
        }

        Book new_book = book_data.getAs(book_data);
        new_book.setAuthor(author);
        author.getBooksLaunched().add(new_book);
        // don't save right now, save after user add it to shelf
        return new_book;
    }

    public Book getBookFromDatabase(String book_api_id) {
        Optional<Book> book_db = getBookByApiId(book_api_id);

        return book_db.orElseGet(() -> addBookIntoDatabase(book_api_id));
    }

    public void saveBookIntoUserShelf(Book book, User user) {
        if (book.getUsers().contains(user)) {
            throw new BookAlreadyOnShelf("This book is already on your shelf.");
        }
        book.getUsers().add(user);
        user.getBooks().add(book);

        saveBook(book);
        userRepository.save(user);
    }

    public List<BookDTO> getAllBooksFromUser(User user) {
        List<Book> books = bookRepository.findBooksByUsers(user);
        List<Book> filteredBooks = books.stream()
                .map(book -> {

                    Set<Review> reviews = book.getReviews().stream()
                            .filter(review -> review.getUser().equals(user))
                            .collect(Collectors.toSet());

                    Book filteredBook = new Book(
                            book.getId(), book.getTitle(), book.getPublishedDate(),
                            book.getPublisher(), book.getSummary(), book.getTotalPages(), book.getPosterUrl());
                    filteredBook.setReviews(reviews);
                    filteredBook.setAuthor(book.getAuthor());

                    return filteredBook;
                })
                .toList();
        return convertToBookDTOList(filteredBooks);
    }

    public void deleteBookFromUserShelf(Book book, User user) {
        if (!book.getUsers().contains(user))
            throw new BookNotOnUserShelf("This book isn't on your shelf!");

        book.getUsers().remove(user);
        user.getBooks().remove(book);

        List<Review> bookReviews = book.getReviews().stream()
                .filter(review -> review.getUser().equals(user))
                .toList();

        bookReviews.forEach(r -> {
            book.getReviews().remove(r);
            user.getReviews().remove(r);
            reviewRepository.delete(r);
        });

        saveBook(book);
        userRepository.save(user);
    }

// ----------------------------------------------------------------------

    private void saveBook(Book book) {
        bookRepository.save(book);
    }

    private void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    public Author getAuthorByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }

    public Book getBookById(String bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("This book does not exist in our data."));
    }

    public Optional<Book> getBookByApiId(String id) {
        return bookRepository.findByIdContainingIgnoreCase(id);
    }

    private BookDTO convertToDto(Book book) {
        if (!book.getReviews().isEmpty())
            return new BookDTO(
                    book.getId(), book.getTitle(), book.getPublishedDate(),
                    book.getPublisher(), book.getSummary(), book.getTotalPages(),
                    book.getAuthor().getName(), book.getPosterUrl(), book.getReviews().stream().map(ReviewDTO::new).toList()
            );
        return new BookDTO(
                book.getId(), book.getTitle(), book.getPublishedDate(),
                book.getPublisher(), book.getSummary(), book.getTotalPages(),
                book.getAuthor().getName(), book.getPosterUrl(), new ArrayList<>()
        );
    }

    private List<BookDTO> convertToBookDTOList(List<Book> all) {
        return all.stream()
                .map(this::convertToDto
                ).collect(Collectors.toList());
    }
}
