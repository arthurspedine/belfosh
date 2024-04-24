package br.com.spedine.bookshelf.controller;

import br.com.spedine.bookshelf.dto.BookDTO;
import br.com.spedine.bookshelf.dto.BookJSONDTO;
import br.com.spedine.bookshelf.model.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService service;

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
    public ResponseEntity<Void> addBook(@RequestBody BookJSONDTO bookJSONDTO) {
        service.saveBook(bookJSONDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
