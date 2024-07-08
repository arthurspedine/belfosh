package br.com.spedine.bookshelf.old.dto;

import java.time.LocalDate;

public record BookDTO(
        Long id,
        String title,
        LocalDate publishedDate,
        String publisher,
        String summary,
        Integer totalPages,
        AuthorDTO author,
        String poster_url
) {
}
