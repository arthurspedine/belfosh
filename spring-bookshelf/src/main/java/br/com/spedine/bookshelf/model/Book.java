package br.com.spedine.bookshelf.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Books")
@Table(name = "books")
public class Book {

    @Id
    private String id;

    @JoinColumn(nullable = false)
    private String title;

    @JoinColumn(nullable = false)
    private LocalDate publishedDate;

    @JoinColumn(nullable = false)
    private String publisher;

    @Column(length = 3000)
    private String summary;

    @JoinColumn(nullable = false)
    private Integer totalPages;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author; // ManyToOne

    @Column(name = "poster_url", nullable = false)
    private String posterUrl;

    @ManyToMany(mappedBy = "books")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    public Book() {
    }

    public Book(String id, String title, LocalDate publishedDate, String publisher, String summary, Integer totalPages, String poster_url) {
        this.id = id;
        this.title = title;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.summary = summary;
        this.totalPages = totalPages;
        this.posterUrl = poster_url;
    }

    public String getId() {
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

    public Set<User> getUsers() {
        return users;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

}
