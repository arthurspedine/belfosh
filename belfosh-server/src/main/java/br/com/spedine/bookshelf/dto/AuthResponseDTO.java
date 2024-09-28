package br.com.spedine.bookshelf.dto;

import br.com.spedine.bookshelf.model.User;

public record AuthResponseDTO(
        String username,
        String email,
        String token
) {

    public AuthResponseDTO(User user, String jwt) {
        this(user.getUsername(), user.getLogin(), jwt);
    }
}
