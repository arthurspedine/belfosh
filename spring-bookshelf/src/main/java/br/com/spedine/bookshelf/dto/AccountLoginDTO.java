package br.com.spedine.bookshelf.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccountLoginDTO(

        @NotBlank
        @Email
        String login,
        @NotBlank
        String password
) {
}
