package com.example.springboottemplate.controller;

import com.example.springboottemplate.model.response.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public ResponseEntity<GenericResponse<String>> helloWorld() {
        return ResponseEntity.ok(GenericResponse.empty("Hello, World!"));
    }
}
