package br.com.spedine.bookshelf.repository;

import br.com.spedine.bookshelf.old.model.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Modifying
    @Transactional
    @Query("delete from Review r where r.id = :id")
    void deteleReviewById(Long id);
}
