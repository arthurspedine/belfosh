package br.com.spedine.bookshelf.controller;

import br.com.spedine.bookshelf.dto.BookJSONDTO;
import br.com.spedine.bookshelf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("/{name}")
    public List<BookJSONDTO> getBooksFromGoogleBooks(@PathVariable String name){
        return service.getAllJsonBooksFromName(name);
    }

}
