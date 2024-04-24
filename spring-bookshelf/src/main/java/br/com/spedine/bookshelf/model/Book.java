package br.com.spedine.bookshelf.model;

import br.com.spedine.bookshelf.dto.BookJSONDTO;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String publishedDate;
    private String publisher;
    private String summary;
    private Integer totalPages;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> userReview; // OneToMany
//    private Author author; // ManyToOne
    private String author;
    private String poster_url;

    public Book() {}


    public Book(String title, String publishedDate, String publisher, String summary, Integer totalPages, String author, String poster_url) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.summary = summary;
        this.totalPages = totalPages;
        this.author = author;
        this.poster_url = poster_url;
    }

}
