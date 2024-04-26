package br.com.spedine.bookshelf.dto;

import br.com.spedine.bookshelf.model.Author;
import br.com.spedine.bookshelf.model.Book;

public record BookDTO(
        Long id,
        String title,
        String publishedDate,
        String publisher,
        String summary,
        Integer totalPages,
        AuthorDTO author,
        String poster_url
) {
}
