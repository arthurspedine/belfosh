package br.com.spedine.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
        String title,
        List<String> authors,
        String publisher,
        String publishedDate,
        @JsonAlias("description")
        String summary,
        @JsonAlias("pageCount")
        Integer totalPages,
        HashMap<String, String> imageLinks
) {
}
