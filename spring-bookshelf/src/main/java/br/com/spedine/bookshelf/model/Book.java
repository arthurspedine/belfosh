package br.com.spedine.bookshelf.model;

import java.util.Date;
import java.util.List;

public class Book {
    private String name;
    private Date yearRealeased;
    private String plot;
    private Integer totalPages;
    private Double rating;
    private List<Review> userReview; // OneToMany

    private Author author; // ManyToOne

}
