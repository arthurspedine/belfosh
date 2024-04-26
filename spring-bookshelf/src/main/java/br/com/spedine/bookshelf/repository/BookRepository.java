package br.com.spedine.bookshelf.repository;

import br.com.spedine.bookshelf.model.Author;
import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByAuthor(Author author);

    @Query("select r from Book b join b.userReview r where r.id = :id")
    List<Review> findReviewByBookId(Long id);

//    Optional<Book> findByAuthorContainingIgnoreCase(String author_name);
}
