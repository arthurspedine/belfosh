package br.com.spedine.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

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
