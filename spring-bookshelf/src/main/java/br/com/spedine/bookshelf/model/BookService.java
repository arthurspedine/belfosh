package br.com.spedine.bookshelf.model;

import br.com.spedine.bookshelf.dto.AuthorDTO;
import br.com.spedine.bookshelf.dto.BookDTO;
import br.com.spedine.bookshelf.dto.BookJSONDTO;
import br.com.spedine.bookshelf.repository.AuthorRepository;
import br.com.spedine.bookshelf.repository.BookRepository;
import br.com.spedine.bookshelf.service.DataConverter;
import br.com.spedine.bookshelf.service.RequestAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    DataConverter dataConverter = new DataConverter();

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<BookJSONDTO> getAllJsonBooksFromName(String name) {
        ItemsData data = dataConverter.getData(RequestAPI.getJsonData(name), ItemsData.class);
        return data.items().stream()
                .filter(v -> v != null &&
                        v.volumeInfo().imageLinks() != null &&
                        v.volumeInfo().authors() != null &&
                        !v.volumeInfo().authors().isEmpty() &&
                        v.volumeInfo().publishedDate() != null &&
                        v.volumeInfo().summary() != null)
                .map(v -> new BookJSONDTO(
                        v.id(),
                        v.volumeInfo().title(), v.volumeInfo().publishedDate(), v.volumeInfo().publisher(),
                        v.volumeInfo().summary(), v.volumeInfo().totalPages(),
                        v.volumeInfo().authors().get(0), v.volumeInfo().imageLinks().get("thumbnail")
                )).toList();
    }

    public BookJSONDTO getBookFromJsonId(String name, String id) {
        ItemsData data = dataConverter.getData(RequestAPI.getJsonData(name), ItemsData.class);
        try {
            return convertVolumeInfoToBookJsonTDO(data.items().stream().filter(v -> v.id().equalsIgnoreCase(id)).toList().get(0));
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public List<BookDTO> getAllSelfBookshelf() {
        return convertToBookDTOList(bookRepository.findAll());
    }

    public BookDTO saveBook(Book book, String authorName) {
        Author author = authorRepository.findByNameContainingIgnoreCase(authorName);
        // DEBUG HERE !
        if (author == null) {
            author = new Author();
            author.setName(authorName);
        }
        book.setAuthor(author);
        author.getBooksLaunched().add(book);
        authorRepository.save(author);
        bookRepository.save(book);
        return convertToBookDTO(book);
    }

    public List<String> getAllAuthors() {
        return bookRepository.findAllAuthors();
    }

    public String getAuthorByName(String name) {
        System.out.println(name);
//        Optional<Book> book = repository.findByAuthorContainingIgnoreCase(name);
//        if (book.isPresent()) {
//            return book.get().getAuthor();
//        }
        return "There is not author with this name!";
    }

    private BookJSONDTO convertVolumeInfoToBookJsonTDO(VolumeData v) {
        return new BookJSONDTO(v.id(),
                v.volumeInfo().title(), v.volumeInfo().publishedDate(), v.volumeInfo().publisher(),
                v.volumeInfo().summary(), v.volumeInfo().totalPages(),
                v.volumeInfo().authors().get(0), v.volumeInfo().imageLinks().get("thumbnail"));
    }

    private BookDTO convertToBookDTO(Book book) {
        return new BookDTO(
                book.getId(), book.getTitle(), book.getPublishedDate(),
                book.getPublisher(), book.getSummary(), book.getTotalPages(),
                new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getName()),
                book.getPoster_url()
        );
    }

    private List<BookDTO> convertToBookDTOList(List<Book> all) {
        return all.stream()
                .map(b -> new BookDTO(
                        b.getId(), b.getTitle(), b.getPublishedDate(),
                        b.getPublisher(), b.getSummary(), b.getTotalPages(),
                        new AuthorDTO(b.getAuthor().getId(), b.getAuthor().getName()),
                        b.getPoster_url()
                )).collect(Collectors.toList());
    }
}
