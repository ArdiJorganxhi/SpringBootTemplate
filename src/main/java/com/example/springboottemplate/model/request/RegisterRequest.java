package com.example.springboottemplate.model.request;

public record RegisterRequest(
        String name,
        String surname,
        String email,
        String password
) {
}
