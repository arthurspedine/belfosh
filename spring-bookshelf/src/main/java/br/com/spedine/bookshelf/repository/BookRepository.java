package br.com.spedine.bookshelf.repository;

import br.com.spedine.bookshelf.old.model.Author;
import br.com.spedine.bookshelf.model.Book;
import br.com.spedine.bookshelf.old.model.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByAuthor(Author author);

    @Query("select r from Book b join b.userReview r where r.book.id = :id")
    List<Review> findReviewByBookId(Long id);

    @Modifying
    @Transactional
    @Query("delete from Book b where b.id = :id")
    void deleteBookById(Long id);

}
