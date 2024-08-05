package br.com.spedine.bookshelf.dto;

public record CreateReviewDTO(
        String book_id,
        String description,
        Double rating
) {
}
