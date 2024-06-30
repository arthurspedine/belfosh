package br.com.spedine.bookshelf.old.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReviewDTO(
        Long id,
        String comment,
        Double rating,
        LocalDate datePublished,
        Long book_id
) {
}
