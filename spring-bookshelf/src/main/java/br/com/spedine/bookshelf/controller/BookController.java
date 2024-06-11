package br.com.spedine.bookshelf.controller;

import br.com.spedine.bookshelf.dto.AuthorDTO;
import br.com.spedine.bookshelf.dto.BookDTO;
import br.com.spedine.bookshelf.dto.BookJSONDTO;
import br.com.spedine.bookshelf.dto.ReviewDTO;
import br.com.spedine.bookshelf.model.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/{name}")
    public List<BookJSONDTO> getAllJsonBooks(@PathVariable String name) {
        return service.getAllJsonBooksFromName(name);
    }

    @GetMapping("/{name}/{id}")
    public BookJSONDTO getJsonBookInfo(@PathVariable String name, @PathVariable String id){
        return service.getBookFromJsonId(name, id);
    }

    @GetMapping("/self/all")
    public List<BookDTO> getAllSelfBookshelf() {
        return service.getAllSelfBookshelf();
    }

    @PostMapping(path = "/self/add", consumes = {"application/json"})
    public ResponseEntity<BookDTO> addBook(@RequestBody BookJSONDTO bookJSONDTO) {
        return ResponseEntity.ok(service.saveBook(bookJSONDTO.getAs(), bookJSONDTO.author()));
    }

    @GetMapping("/self/author/all")
    public List<AuthorDTO> getAllAuthors() {
        return service.getAllAuthors();
    }

    @GetMapping("/self/author/{id}")
    public ResponseEntity<List<BookDTO>> getAuthorByName(@PathVariable Long id) {
        List<BookDTO> books = service.getBooksByAuthorId(id);
        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/self/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = service.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping("/self/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewsByBookId(@PathVariable Long id) {
        List<ReviewDTO> reviews = service.getReviewsByBookId(id);
        if (reviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviews);
    }

    @PostMapping(path = "/self/reviews/add", consumes = {"application/json"})
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(service.saveReview(reviewDTO));
    }

    @DeleteMapping(path = "/self/{id}/delete")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        service.deleteBookById(id);
        return ResponseEntity.ok("Book deleted successfully!");
    }
}
