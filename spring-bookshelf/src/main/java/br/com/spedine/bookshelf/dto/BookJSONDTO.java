package br.com.spedine.bookshelf.dto;

import br.com.spedine.bookshelf.model.Book;

public record BookJSONDTO(
        String title,
        String publishedDate,
        String publisher,
        String summary,
        Integer totalPages,
        String author,
        String poster_url,
        String api_id
) {
    public Book getAs(BookJSONDTO data) {
        return new Book(
                data.title(), data.publishedDate(), data.publisher(),
                data.summary(), data.totalPages(),
                data.poster_url(), data.api_id()
        );
    }
}
