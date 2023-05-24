package dev.ardijorganxhi.springboottemplate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/index")
public class IndexController {

    @GetMapping
    public String helloWorld() {
        return "<h1>Hello World! </h1>";
    }
}
