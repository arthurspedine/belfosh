package br.com.spedine.bookshelf.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> booksLaunched = new ArrayList<>(); // OneToMany

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooksLaunched() {
        return booksLaunched;
    }

//    public void setBooksLaunched(List<Book> booksLaunched) {
//        booksLaunched.forEach(b -> b.setAuthor(this));
//        this.booksLaunched = booksLaunched;
//    }
}
