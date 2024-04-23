package br.com.spedine.bookshelf.dto;

public record BookJSONDTO(
        String id,
        String title,
        String publishedDate,
        String publisher,
        String plot,
        Integer totalPages,
        String author,
        String poster_url
) {
}
