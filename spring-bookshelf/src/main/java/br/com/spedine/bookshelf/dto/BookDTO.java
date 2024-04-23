package br.com.spedine.bookshelf.dto;

public record BookDTO(
        Long id,
        String title,
        String publishedDate,
        String publisher,
        String plot,
        Integer totalPages,
        String author,
        String poster_url
) {
}
