package com.example.springboottemplate.model.dto;

public record UserDto(
        Integer id,
        String name,
        String surname,
        String email
) {
}
