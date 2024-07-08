package br.com.spedine.bookshelf.controller;

import br.com.spedine.bookshelf.dto.BookAddedDTO;
import br.com.spedine.bookshelf.dto.BookJSONDTO;
import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    //  WHEN USER HAS Bearer Token expired or invalid, it returns 403 (user should remove the token or login again
    @GetMapping("/{name}")
    public ResponseEntity<List<BookJSONDTO>> getBooksFromGoogleBooks(@PathVariable String name) {
        return ResponseEntity.ok(service.getAllJsonBooksFromName(name));
    }

    @PostMapping("/add")
    public ResponseEntity<BookAddedDTO> addBookInUserShelf(
            @RequestBody BookJSONDTO data,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        Book book = service.getBookFromDatabase(data);

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();



        return ResponseEntity.ok(new BookAddedDTO(1L, 2L));
    }

}
