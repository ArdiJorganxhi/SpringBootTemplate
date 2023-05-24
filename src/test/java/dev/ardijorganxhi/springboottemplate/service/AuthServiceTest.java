package dev.ardijorganxhi.springboottemplate.service;

import dev.ardijorganxhi.springboottemplate.entity.User;
import dev.ardijorganxhi.springboottemplate.mapper.AuthMapper;
import dev.ardijorganxhi.springboottemplate.model.request.LoginRequest;
import dev.ardijorganxhi.springboottemplate.model.request.RegisterRequest;
import dev.ardijorganxhi.springboottemplate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthMapper authMapper;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;


    @Test
    public void it_should_register() {
        RegisterRequest request = RegisterRequest.builder()
                .name("name")
                .surname("surname")
                .email("user@gmail.com")
                .password("user123")
                .build();

        User user = User.builder()
                .name("name")
                .surname("surname")
                .email("user@gmail.com")
                .password("user123")
                .build();

        when(authMapper.register(request)).thenReturn(user);

        authService.register(request);

        verify(authMapper).register(request);
        verify(userRepository).save(user);

    }

    @Test
    public void it_should_login() {
        LoginRequest request = LoginRequest.builder()
                .email("user@gmail.com")
                .password("user123")
                .build();

        User user = User.builder()
                .email("user@gmail.com")
                .password("user123")
                .build();

        when(userService.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        authService.login(request);

        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        verify(userService).findByEmail(request.getEmail());
        verify(tokenService).generateToken(user);
    }
}
