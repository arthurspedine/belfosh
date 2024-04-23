package br.com.spedine.bookshelf.model;

import br.com.spedine.bookshelf.dto.BookDTO;
import br.com.spedine.bookshelf.dto.BookJSONDTO;
import br.com.spedine.bookshelf.repository.BookRepository;
import br.com.spedine.bookshelf.service.DataConverter;
import br.com.spedine.bookshelf.service.RequestAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class BookService {

    DataConverter dataConverter = new DataConverter();

    @Autowired
    private BookRepository repository;

    public List<BookJSONDTO> getAllJsonBooksFromName(String name) {
        ItemsData data = dataConverter.getData(RequestAPI.getJsonData(name), ItemsData.class);
        return data.items().stream()
                .filter(v -> v != null &&
                        v.volumeInfo().imageLinks() != null &&
                        v.volumeInfo().authors() != null &&
                        !v.volumeInfo().authors().isEmpty() &&
                        v.volumeInfo().publishedDate() != null &&
                        v.volumeInfo().plot() != null)
                .map(v -> new BookJSONDTO(
                        v.id(),
                        v.volumeInfo().title(), v.volumeInfo().publishedDate(), v.volumeInfo().publisher(),
                        v.volumeInfo().plot(), v.volumeInfo().totalPages(),
                        v.volumeInfo().authors().get(0), v.volumeInfo().imageLinks().get("thumbnail")
                )).toList();
    }

    public BookJSONDTO getBookFromJsonId(String name, String id) {
        ItemsData data = dataConverter.getData(RequestAPI.getJsonData(name), ItemsData.class);
        try {
            return convertVolumeInfoToBookJsonTDO(data.items().stream().filter(v -> v.id().equalsIgnoreCase(id)).toList().get(0));
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public List<BookDTO> getAllSelfBookshelf() {
        return null;
    }

    private BookJSONDTO convertVolumeInfoToBookJsonTDO(VolumeData v) {
        return new BookJSONDTO(v.id(),
                v.volumeInfo().title(), v.volumeInfo().publishedDate(), v.volumeInfo().publisher(),
                v.volumeInfo().plot(), v.volumeInfo().totalPages(),
                v.volumeInfo().authors().get(0), v.volumeInfo().imageLinks().get("thumbnail"));
    }
}
