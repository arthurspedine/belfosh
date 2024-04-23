package br.com.spedine.bookshelf.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Date yearRealeased;
    private String plot;
    private Integer totalPages;
    private Double rating;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> userReview; // OneToMany
    @ManyToOne
    private Author author; // ManyToOne
}
