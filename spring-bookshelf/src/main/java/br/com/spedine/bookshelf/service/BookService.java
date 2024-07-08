package br.com.spedine.bookshelf.service;

import br.com.spedine.bookshelf.dto.BookJSONDTO;
import br.com.spedine.bookshelf.infra.exception.BookAlreadyOnShelf;
import br.com.spedine.bookshelf.infra.exception.BookNotOnUserShelf;
import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.model.Review;
import br.com.spedine.bookshelf.model.User;
import br.com.spedine.bookshelf.model.api.ItemsData;
import br.com.spedine.bookshelf.dto.AuthorDTO;
import br.com.spedine.bookshelf.dto.BookDTO;
import br.com.spedine.bookshelf.model.Author;
import br.com.spedine.bookshelf.repository.AuthorRepository;
import br.com.spedine.bookshelf.repository.BookRepository;
import br.com.spedine.bookshelf.repository.ReviewRepository;
import br.com.spedine.bookshelf.repository.UserRepository;
import br.com.spedine.bookshelf.service.api.DataConverter;
import br.com.spedine.bookshelf.service.api.RequestAPI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public List<BookJSONDTO> getAllJsonBooksFromName(String name) {
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
                .map(v -> new BookJSONDTO(
                        v.volumeInfo().title(), v.volumeInfo().publishedDate(), v.volumeInfo().publisher(),
                        v.volumeInfo().summary(), v.volumeInfo().totalPages(),
                        v.volumeInfo().authors().get(0), v.volumeInfo().imageLinks().get("thumbnail"),
                        v.id()
                )).toList();
    }

    public void addBookIntoDatabase(BookJSONDTO data) {
        Author author = getAuthorByName(data.author());
        if (author == null) {
            author = new Author();
            author.setName(data.author());
            author.setBooksLaunched(new ArrayList<>());
            saveAuthor(author);
        }
        Book new_book = data.getAs(data);
        new_book.setAuthor(author);
        author.getBooksLaunched().add(new_book);
        saveBook(new_book);
    }

    public Book getBookFromDatabase(BookJSONDTO data) {
        if (getBookByApiId(data.api_id()).isEmpty())
            addBookIntoDatabase(data);

        return getBookByApiId(data.api_id()).get();
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
        return convertToBookDTOList(bookRepository.findBooksByUsers(user));
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

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("This book does not exist in our data."));
    }

    public Optional<Book> getBookByApiId(String id) {
        return bookRepository.findByApiIdContainingIgnoreCase(id);
    }

    private BookDTO convertToDto(Book book) {
        return new BookDTO(
                book.getId(), book.getTitle(), book.getPublishedDate(),
                book.getPublisher(), book.getSummary(), book.getTotalPages(),
                new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getName()),
                book.getPoster_url()
        );
    }

    private List<BookDTO> convertToBookDTOList(List<Book> all) {
        return all.stream()
                .map(this::convertToDto
                ).collect(Collectors.toList());
    }
}
