package br.com.spedine.bookshelf.service;

import br.com.spedine.bookshelf.infra.security.TokenService;
import br.com.spedine.bookshelf.model.User;
import br.com.spedine.bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private TokenService service;

    @Autowired
    private UserRepository repository;

    public User getUserByLogin(String header) {
        String login = service.getSubject(header.replace("Bearer ", ""));
        UserDetails userDetails = repository.findByLogin(login);
        if (userDetails != null) {
            return (User) userDetails;
        } else {
            throw new UsernameNotFoundException("User not found! Please check your login");
        }
    }
}
