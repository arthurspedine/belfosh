package br.com.spedine.bookshelf.model;

import java.util.List;

public class Author {
    private String name;
    private List<Book> booksLaunched; // OneToMany
}
