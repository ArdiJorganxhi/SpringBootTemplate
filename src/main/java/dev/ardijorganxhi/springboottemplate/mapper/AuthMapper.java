package dev.ardijorganxhi.springboottemplate.mapper;

import dev.ardijorganxhi.springboottemplate.config.PasswordEncoder;
import dev.ardijorganxhi.springboottemplate.entity.User;
import dev.ardijorganxhi.springboottemplate.model.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;
    public User register(RegisterRequest request) {
        return User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.bCryptPasswordEncoder().encode(request.getPassword()))
                .build();
    }
}
