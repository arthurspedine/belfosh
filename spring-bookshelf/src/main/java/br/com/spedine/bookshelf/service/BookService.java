package br.com.spedine.bookshelf.service;

import br.com.spedine.bookshelf.dto.BookJSONDTO;
import br.com.spedine.bookshelf.model.api.ItemsData;
import br.com.spedine.bookshelf.service.api.DataConverter;
import br.com.spedine.bookshelf.service.api.RequestAPI;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    DataConverter dataConverter = new DataConverter();

    public List<BookJSONDTO> getAllJsonBooksFromName(String name) {
        ItemsData data = dataConverter.getData(RequestAPI.getJsonData(name), ItemsData.class);
        return data.items().stream()
                .filter(v -> v != null &&
                        v.volumeInfo().imageLinks() != null &&
                        v.volumeInfo().authors() != null &&
                        !v.volumeInfo().authors().isEmpty() &&
                        v.volumeInfo().publishedDate() != null &&
                        v.volumeInfo().summary() != null)
                .map(v -> new BookJSONDTO(
                        v.volumeInfo().title(), v.volumeInfo().publishedDate(), v.volumeInfo().publisher(),
                        v.volumeInfo().summary(), v.volumeInfo().totalPages(),
                        v.volumeInfo().authors().get(0), v.volumeInfo().imageLinks().get("thumbnail")
                )).toList();
    }
}
