package br.com.spedine.bookshelf.model;

import br.com.spedine.bookshelf.old.model.Author;
import br.com.spedine.bookshelf.old.model.Review;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private LocalDate publishedDate;
    private String publisher;
    @Column(length = 2000)
    private String summary;
    private Integer totalPages;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> userReview; // OneToMany
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author; // ManyToOne
    private String poster_url;
    private String apiId;

    @ManyToMany(mappedBy = "books")
    private Set<User> users;

    public Book() {
    }

    public Book(String title, String publishedDate, String publisher, String summary, Integer totalPages, Author author, String poster_url) {
        this.title = title;
        this.publishedDate = LocalDate.parse(publishedDate);
        this.publisher = publisher;
        this.summary = summary;
        this.totalPages = totalPages;
        this.author = author;
        this.poster_url = poster_url;
    }

    // JSONDTO
    public Book(String title, String publishedDate, String publisher, String summary, Integer totalPages, String poster_url, String apiId) {
        this.title = title;
        this.publishedDate = LocalDate.parse(publishedDate);
        this.publisher = publisher;
        this.summary = summary;
        this.totalPages = totalPages;
        this.poster_url = poster_url;
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

    public List<Review> getUserReview() {
        return userReview;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public Set<User> getUsers() {
        return users;
    }
}
