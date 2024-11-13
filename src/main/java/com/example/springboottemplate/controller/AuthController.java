package com.example.springboottemplate.controller;

import com.example.springboottemplate.model.GenericResponse;
import com.example.springboottemplate.model.enums.MessageResponse;
import com.example.springboottemplate.model.request.LoginRequest;
import com.example.springboottemplate.model.request.RegisterRequest;
import com.example.springboottemplate.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<String>> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return GenericResponse.success(MessageResponse.REGISTRATION_IS_SUCCESSFUL, "");
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<String>> login(@RequestBody LoginRequest request) throws Exception {
        final String token = authService.login(request);
        return GenericResponse.success(MessageResponse.LOGIN_IS_SUCCESSFUL, token);

    }

}
