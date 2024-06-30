package br.com.spedine.bookshelf.model.api;

import br.com.spedine.bookshelf.model.BookData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VolumeData(
        String id,
        BookData volumeInfo
) {
}
