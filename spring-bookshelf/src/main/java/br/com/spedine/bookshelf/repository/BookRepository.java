package br.com.spedine.bookshelf.repository;

import br.com.spedine.bookshelf.model.User;
import br.com.spedine.bookshelf.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {

    Optional<Book> findByIdContainingIgnoreCase(String title);

    List<Book> findBooksByUsers(User user);

}
