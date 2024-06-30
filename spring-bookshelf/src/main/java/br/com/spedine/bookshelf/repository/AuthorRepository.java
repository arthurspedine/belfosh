package br.com.spedine.bookshelf.repository;

import br.com.spedine.bookshelf.old.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByNameContainingIgnoreCase(String author);
}
