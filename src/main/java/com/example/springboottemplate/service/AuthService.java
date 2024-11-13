package com.example.springboottemplate.service;

import com.example.springboottemplate.entity.User;
import com.example.springboottemplate.model.error.GenericErrorMessage;
import com.example.springboottemplate.model.exception.NotFoundException;
import com.example.springboottemplate.model.request.LoginRequest;
import com.example.springboottemplate.model.request.RegisterRequest;
import com.example.springboottemplate.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Transactional
    public void register(RegisterRequest request) {
        final User user = User.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);
    }

    public String login(LoginRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (DisabledException e) {
            throw new Exception("Disabled");
        }
        final User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new NotFoundException(GenericErrorMessage
                        .builder()
                        .message(String.format("User with email %s not found", request.email()))
                        .build())
        );;
        return tokenService.generateToken(user);
    }
}
