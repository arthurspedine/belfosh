package br.com.spedine.bookshelf.service;

import br.com.spedine.bookshelf.dto.BookJSONDTO;
import br.com.spedine.bookshelf.infra.exception.BookAlreadyInShelf;
import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.model.User;
import br.com.spedine.bookshelf.model.api.ItemsData;
import br.com.spedine.bookshelf.old.dto.AuthorDTO;
import br.com.spedine.bookshelf.old.dto.BookDTO;
import br.com.spedine.bookshelf.old.model.Author;
import br.com.spedine.bookshelf.repository.AuthorRepository;
import br.com.spedine.bookshelf.repository.BookRepository;
import br.com.spedine.bookshelf.repository.UserRepository;
import br.com.spedine.bookshelf.service.api.DataConverter;
import br.com.spedine.bookshelf.service.api.RequestAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    DataConverter dataConverter = new DataConverter();

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

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

    private void saveBook(Book book) {
        bookRepository.save(book);
    }

    private void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    public Author getAuthorByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
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


    public void saveBookIntoUserShelf(Book book, User user) {
        if (book.getUsers().contains(user)) {
            throw new BookAlreadyInShelf("This book is already on your shelf.");
        }
        book.getUsers().add(user);
        user.getBooks().add(book);

        bookRepository.save(book);
        userRepository.save(user);
    }
}
