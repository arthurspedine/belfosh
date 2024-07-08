package br.com.spedine.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReviewDTO(
        Long id,
        String description,
        Double rating,
        LocalDate datePublished,
        Long book_id
) {
}
