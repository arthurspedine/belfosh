package br.com.spedine.bookshelf.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccountRegisterDTO(

        @NotBlank
        String username,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {
}
