package br.com.spedine.bookshelf.dto;

import br.com.spedine.bookshelf.model.Book;

public record BookDTO(
        Long id,
        String title,
        String publishedDate,
        String publisher,
        String summary,
        Integer totalPages,
        String author,
        String poster_url
) {
}
