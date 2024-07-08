package br.com.spedine.bookshelf.controller;

import br.com.spedine.bookshelf.dto.BookAddedDTO;
import br.com.spedine.bookshelf.dto.BookJSONDTO;
import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.model.User;
import br.com.spedine.bookshelf.service.BookService;
import br.com.spedine.bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    //  WHEN USER HAS Bearer Token expired or invalid, it returns 403 (user should remove the token or login again
    @GetMapping("/{name}")
    public ResponseEntity<List<BookJSONDTO>> getBooksFromGoogleBooks(@PathVariable String name) {
        return ResponseEntity.ok(bookService.getAllJsonBooksFromName(name));
    }

    @PostMapping("/add")
    public ResponseEntity<BookAddedDTO> addBookInUserShelf(
            @RequestBody BookJSONDTO data,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        Book book = bookService.getBookFromDatabase(data);
        User user = userService.getUserByLogin(authHeader);
        bookService.saveBookIntoUserShelf(book, user);

        return ResponseEntity.ok(new BookAddedDTO(user.getId(), book.getId()));
    }

}
