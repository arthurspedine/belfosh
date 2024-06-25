package br.com.spedine.bookshelf.controller;

import br.com.spedine.bookshelf.dto.AccountLoginDTO;
import br.com.spedine.bookshelf.dto.AccountRegisterDTO;
import br.com.spedine.bookshelf.dto.AuthResponseDTO;
import br.com.spedine.bookshelf.infra.exception.EmailAlreadyExistsException;
import br.com.spedine.bookshelf.infra.security.TokenService;
import br.com.spedine.bookshelf.model.User;
import br.com.spedine.bookshelf.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthenticationController {

    @Autowired
    public AuthenticationManager manager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService service;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AccountLoginDTO data) {
        var user = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = manager.authenticate(user);
        String jwtToken = service.genToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new AuthResponseDTO((User) auth.getPrincipal(), jwtToken));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid AccountRegisterDTO data) {
        if (repository.findByLogin(data.email()) != null)
            throw new EmailAlreadyExistsException("An account with this email already exists.");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.username(), data.email(), encryptedPassword);
        repository.save(user);
        return ResponseEntity.status(201).build();
    }
}
