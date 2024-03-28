package com.ethan5.controller;

import com.ethan5.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
public class AuthController {
    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("login")
    public void login(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        service.login(token);
    }
}
