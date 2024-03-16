package com.example.springboottemplate.controller;

import com.example.springboottemplate.model.response.AuthenticationResponse;
import com.example.springboottemplate.model.response.GenericResponse;
import com.example.springboottemplate.model.request.LoginRequest;
import com.example.springboottemplate.model.request.RegisterRequest;
import com.example.springboottemplate.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<String>> register(@Valid @RequestBody RegisterRequest request) {
        authenticationService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GenericResponse.empty("Registration is successful!"));
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> login(@Valid @RequestBody LoginRequest request) throws Exception {
        final AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.ok(GenericResponse.success(response));
    }
}
