package dev.ardijorganxhi.springboottemplate.service;

import dev.ardijorganxhi.springboottemplate.entity.User;
import dev.ardijorganxhi.springboottemplate.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void it_should_find_by_email() {
        String email = "user@gmail.com";

        Optional<User> user = Optional.ofNullable(User.builder()
                .email("user@gmail.com")
                .build());

        when(userRepository.findByEmail(email)).thenReturn(user);

        Optional<User> result = userService.findByEmail(email);

        assertEquals(result, user);
    }
}
