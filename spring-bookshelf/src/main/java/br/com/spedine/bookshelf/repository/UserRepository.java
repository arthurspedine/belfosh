package br.com.spedine.bookshelf.repository;

import br.com.spedine.bookshelf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String subject);
}
