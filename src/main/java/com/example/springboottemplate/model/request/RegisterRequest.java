package com.example.springboottemplate.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank @NotNull String name,
        @NotBlank @NotNull String surname,
        @NotBlank @NotNull @Email String email,
        @NotBlank @NotNull String password
) {
}
