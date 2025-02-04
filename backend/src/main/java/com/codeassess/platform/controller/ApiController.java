package com.codeassess.platform.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @CrossOrigin
    @GetMapping("/api/message")
    public String getMessage() {
        return "Hello from Spring Boot!";
    }
}
