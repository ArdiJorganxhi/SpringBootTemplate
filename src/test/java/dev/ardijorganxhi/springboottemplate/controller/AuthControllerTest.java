package dev.ardijorganxhi.springboottemplate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ardijorganxhi.springboottemplate.entity.User;
import dev.ardijorganxhi.springboottemplate.model.request.LoginRequest;
import dev.ardijorganxhi.springboottemplate.model.request.RegisterRequest;
import dev.ardijorganxhi.springboottemplate.service.AuthService;
import dev.ardijorganxhi.springboottemplate.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(authService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void it_should_send_register_request() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .name("name")
                .surname("surname")
                .email("user@gmail.com")
                .password("user123")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    public void it_should_send_login_request() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("user@gmail.com")
                .password("user123")
                .build();

        User user = User.builder()
                .email("user@gmail.com")
                .password("user123")
                .build();

        String token = "token";

        when(authService.login(request)).thenReturn(token);
        when(tokenService.generateToken(user)).thenReturn(token);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ResponseEntity<String> responseEntity = ResponseEntity.ok(token);
        String expectedResponseBody = responseEntity.getBody();

        String responseBody = result.getResponse().getContentAsString().trim();
        assertEquals(expectedResponseBody, responseBody);

        verify(authService).login(request);
    }
}
