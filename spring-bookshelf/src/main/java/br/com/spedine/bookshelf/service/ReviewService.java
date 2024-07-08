package br.com.spedine.bookshelf.service;

import br.com.spedine.bookshelf.dto.CreateReviewDTO;
import br.com.spedine.bookshelf.dto.ReviewDTO;
import br.com.spedine.bookshelf.infra.exception.BookNotOnUserShelf;
import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.model.Review;
import br.com.spedine.bookshelf.model.User;
import br.com.spedine.bookshelf.repository.BookRepository;
import br.com.spedine.bookshelf.repository.ReviewRepository;
import br.com.spedine.bookshelf.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void addReview(Book book, User user, CreateReviewDTO data) {
        if (!book.getUsers().contains(user))
            throw new BookNotOnUserShelf("This book isn't on your shelf!");

        Review review = new Review(book, user, data.description(), data.rating(), LocalDate.now());
        System.out.println(review);
        book.getReviews().add(review);
        user.getReviews().add(review);
        reviewRepository.save(review);
    }

    public void deleteReview(Book book, User user, Long id) {
        if (!book.getUsers().contains(user))
            throw new BookNotOnUserShelf("This book isn't on your shelf!");
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            if (!book.getReviews().contains(review.get()))
                throw new EntityNotFoundException("Couldn't find any review about this book");
            book.getReviews().remove(review.get());
            user.getReviews().remove(review.get());
            reviewRepository.delete(review.get());
        } else {
            throw new EntityNotFoundException("There is no review with this id");
        }
    }

    public List<ReviewDTO> getAllReviewsByBookId(Book book, User user) {
        if (!book.getUsers().contains(user))
            throw new BookNotOnUserShelf("This book isn't on your shelf!");
        List<Review> reviews = book.getReviews().stream()
                .filter(review -> review.getUser().equals(user))
                .toList();
        return reviews.stream().map(r -> new ReviewDTO(
                r.getId(), r.getDescription(), r.getRating(),
                r.getReviewDate(), r.getBook().getId()))
                .toList();
    }
}
