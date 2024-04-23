package br.com.spedine.bookshelf.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VolumeData(
        String id,
        @JsonAlias("volumeInfo")
        BookData volumeInfo
) {
}
