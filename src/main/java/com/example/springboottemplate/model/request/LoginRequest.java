package com.example.springboottemplate.model.request;

public record LoginRequest(
        String email,
        String password
) {
}
