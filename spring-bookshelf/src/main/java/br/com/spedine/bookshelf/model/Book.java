package br.com.spedine.bookshelf.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    private String title;

    @JoinColumn(nullable = false)
    private LocalDate publishedDate;

    @JoinColumn(nullable = false)
    private String publisher;

    @Column(length = 2000)
    private String summary;

    @JoinColumn(nullable = false)
    private Integer totalPages;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author; // ManyToOne

    @Column(name = "poster_url", nullable = false)
    private String posterUrl;

    @Column(name = "api_id", nullable = false)
    private String apiId;

    @ManyToMany(mappedBy = "books")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    public Book() {
    }

    public Book(String title, String publishedDate, String publisher, String summary, Integer totalPages, Author author, String poster_url) {
        this.title = title;
        this.publishedDate = LocalDate.parse(publishedDate);
        this.publisher = publisher;
        this.summary = summary;
        this.totalPages = totalPages;
        this.author = author;
        this.posterUrl = poster_url;
    }

    // JSONDTO
    public Book(String title, String publishedDate, String publisher, String summary, Integer totalPages, String poster_url, String apiId) {
        this.title = title;
        this.publishedDate = LocalDate.parse(publishedDate);
        this.publisher = publisher;
        this.summary = summary;
        this.totalPages = totalPages;
        this.posterUrl = poster_url;
        this.apiId = apiId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSummary() {
        return summary;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getApiId() {
        return apiId;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Review> getReviews() {
        return reviews;
    }
}
