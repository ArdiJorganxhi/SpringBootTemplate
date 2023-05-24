package dev.ardijorganxhi.springboottemplate.mapper;

import dev.ardijorganxhi.springboottemplate.config.PasswordEncoder;
import dev.ardijorganxhi.springboottemplate.entity.User;
import dev.ardijorganxhi.springboottemplate.model.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthMapper authMapper;

    @Test
    public void it_should_map_register() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        when(passwordEncoder.bCryptPasswordEncoder()).thenReturn(bCryptPasswordEncoder);

        RegisterRequest request = RegisterRequest.builder()
                .name("name")
                .surname("surname")
                .email("user@gmail.com")
                .password(bCryptPasswordEncoder.encode("user123"))
                .build();

        User user = User.builder()
                .name("name")
                .surname("surname")
                .email("user@gmail.com")
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();

        User result = authMapper.register(request);

        assertEquals(user.getName(), result.getName());
        assertEquals(user.getSurname(), result.getSurname());
        assertEquals(user.getEmail(), result.getEmail());
    }
}
