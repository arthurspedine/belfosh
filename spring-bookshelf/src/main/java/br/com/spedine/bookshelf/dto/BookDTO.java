package br.com.spedine.bookshelf.dto;

import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.model.Review;
import br.com.spedine.bookshelf.model.api.VolumeData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record BookDTO(
        String id,
        String title,
        LocalDate publishedDate,
        String publisher,
        String summary,
        Integer totalPages,
        String author,
        String poster_url,
        List<ReviewDTO> reviews
) {

    public Book getAs(BookDTO data) {
        return new Book(
                data.id(), data.title(), data.publishedDate(), data.publisher(),
                data.summary(), data.totalPages(),
                data.poster_url()
        );
    }

    public BookDTO(VolumeData v) {
        this(v.id(), v.volumeInfo().title(), LocalDate.parse(v.volumeInfo().publishedDate()), v.volumeInfo().publisher(),
                v.volumeInfo().summary(), v.volumeInfo().totalPages(),
                v.volumeInfo().authors().get(0), v.volumeInfo().imageLinks().get("thumbnail"), new ArrayList<>());
    }
}
