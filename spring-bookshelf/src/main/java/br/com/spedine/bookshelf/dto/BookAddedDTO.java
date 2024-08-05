package br.com.spedine.bookshelf.dto;

public record BookAddedDTO(
        Long user_id,
        String book_id
) {
}
