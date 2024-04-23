package br.com.spedine.bookshelf.repository;

import br.com.spedine.bookshelf.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
