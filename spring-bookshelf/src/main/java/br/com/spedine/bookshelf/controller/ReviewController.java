package br.com.spedine.bookshelf.controller;

import br.com.spedine.bookshelf.dto.CreateReviewDTO;
import br.com.spedine.bookshelf.dto.ReviewDTO;
import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.model.User;
import br.com.spedine.bookshelf.service.BookService;
import br.com.spedine.bookshelf.service.ReviewService;
import br.com.spedine.bookshelf.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<Void> createReview(
            @RequestBody CreateReviewDTO data,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        Book book = bookService.getBookById(data.book_id());
        User user = userService.getUserByLogin(authHeader);
        reviewService.addReview(book, user, data);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/delete/{book_id}/{review_id}")
    @Transactional
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long book_id,
            @PathVariable Long review_id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        Book book = bookService.getBookById(book_id);
        User user = userService.getUserByLogin(authHeader);
        reviewService.deleteReview(book, user, review_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{book_id}")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsByBookId(
            @PathVariable Long book_id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        Book book = bookService.getBookById(book_id);
        User user = userService.getUserByLogin(authHeader);

        return ResponseEntity.ok().body(reviewService.getAllReviewsByBookId(book, user));
    }
}
