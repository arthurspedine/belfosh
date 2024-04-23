package br.com.spedine.bookshelf.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Book book; //ManyToOne
    @Column(nullable = false)
    private String comment;
//    private Date reviewDate; // ADD LATER
    private Double rating;
}
