package br.com.spedine.bookshelf.dto;

public record CreateReviewDTO(
        Long book_id,
        String description,
        Double rating
) {
}
