package br.com.spedine.bookshelf.dto;

public record BookAddedDTO(
        Long user_id,
        Long book_id
) {
}
