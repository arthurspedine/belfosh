package br.com.spedine.bookshelf.repository;

import br.com.spedine.bookshelf.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select distinct author from Book")
    List<String> findAllAuthors();

//    Optional<Book> findByAuthorContainingIgnoreCase(String author_name);
}
