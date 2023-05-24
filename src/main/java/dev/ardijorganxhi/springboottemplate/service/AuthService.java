package dev.ardijorganxhi.springboottemplate.service;

import dev.ardijorganxhi.springboottemplate.entity.User;
import dev.ardijorganxhi.springboottemplate.mapper.AuthMapper;
import dev.ardijorganxhi.springboottemplate.model.request.LoginRequest;
import dev.ardijorganxhi.springboottemplate.model.request.RegisterRequest;
import dev.ardijorganxhi.springboottemplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public void register(RegisterRequest request) {
        final User user = authMapper.register(request);
        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return e.getLocalizedMessage();
        }
        final User user = userService.findByEmail(request.getEmail()).orElseThrow();
        return tokenService.generateToken(user);
    }
}
