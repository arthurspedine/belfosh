package br.com.spedine.bookshelf.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemsData(
        List<VolumeData> items
) {
}
