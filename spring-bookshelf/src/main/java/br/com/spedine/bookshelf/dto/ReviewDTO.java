package br.com.spedine.bookshelf.dto;

import br.com.spedine.bookshelf.model.Review;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReviewDTO(
        Long id,
        String book_id,
        String description,
        Double rating,
        LocalDate datePublished
) {
    public ReviewDTO(Review review) {
        this(review.getId(), review.getBook().getId(), review.getDescription(), review.getRating(), review.getReviewDate());
    }
}
