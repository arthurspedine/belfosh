package br.com.spedine.bookshelf.controller;

import br.com.spedine.bookshelf.dto.BookAddedDTO;
import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.model.User;
import br.com.spedine.bookshelf.dto.BookDTO;
import br.com.spedine.bookshelf.service.BookService;
import br.com.spedine.bookshelf.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    //  WHEN USER HAS Bearer Token expired or invalid, it returns 403 (user should remove the token or login again)
    @GetMapping("/search/all") // /search/all?name=...
    public ResponseEntity<List<BookDTO>> getBooksFromGoogleBooks(@RequestParam String name) {
        return ResponseEntity.ok(bookService.getAllBooksFromSearch(name));
    }

    @GetMapping("/search") // /search?id=...
    public ResponseEntity<BookDTO> getSearchedBookById(@RequestParam String id) {
        return ResponseEntity.ok(bookService.getBookDataById(id));
    }

    @SecurityRequirement(name = "bearer-key")
    @PostMapping("/add")
    @Transactional
    public ResponseEntity<BookAddedDTO> addBookInUserShelf(
            @RequestParam String book_id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        Book book = bookService.getBookFromDatabase(book_id);
        User user = userService.getUserByLogin(authHeader);
        bookService.saveBookIntoUserShelf(book, user);

        return ResponseEntity.ok(new BookAddedDTO(user.getId(), book.getId()));
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/user/all")
    public ResponseEntity<List<BookDTO>> getAllBooksFromShelf(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = userService.getUserByLogin(authHeader);
        return ResponseEntity.ok(bookService.getAllBooksFromUser(user));
    }

    @SecurityRequirement(name = "bearer-key")
    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<Void> deleteBookInUserShelf(
            @RequestParam String book_id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        Book book = bookService.getBookById(book_id);
        User user = userService.getUserByLogin(authHeader);
        bookService.deleteBookFromUserShelf(book, user);

        return ResponseEntity.ok().build();

    }
}
