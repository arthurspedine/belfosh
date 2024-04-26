package br.com.spedine.bookshelf.dto;

import br.com.spedine.bookshelf.model.Book;

public record BookJSONDTO(
        String id,
        String title,
        String publishedDate,
        String publisher,
        String summary,
        Integer totalPages,
        String author,
        String poster_url
) {
    public Book getAs(){
        return new Book( // without author
                title, publishedDate, publisher, summary,
                totalPages, poster_url);
    }
}
