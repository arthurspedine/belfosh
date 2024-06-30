package br.com.spedine.bookshelf.old.model;

import br.com.spedine.bookshelf.model.Book;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book; //ManyToOne
    @Column(length = 1500)
    private String comment;
    private Double rating;
    private LocalDate datePublished;

    public Review() {}

    public Review(Book book, String comment, Double rating, LocalDate datePublished) {
        this.book = book;
        this.comment = comment;
        this.rating = rating;
        this.datePublished = datePublished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(LocalDate datePublished) {
        this.datePublished = datePublished;
    }
}
