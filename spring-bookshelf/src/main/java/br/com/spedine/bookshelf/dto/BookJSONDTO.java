package br.com.spedine.bookshelf.dto;

import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.model.api.VolumeData;

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

    public BookJSONDTO(VolumeData v) {
        this(v.volumeInfo().title(), v.volumeInfo().publishedDate(), v.volumeInfo().publisher(),
                v.volumeInfo().summary(), v.volumeInfo().totalPages(),
                v.volumeInfo().authors().get(0), v.volumeInfo().imageLinks().get("thumbnail"),
                v.id());
    }
}
