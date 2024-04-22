package br.com.spedine.bookshelf.model;

import java.util.Date;

public class Review {
    private Book book; //ManyToOne
    private String description;
    private Date reviewDate ;
}
