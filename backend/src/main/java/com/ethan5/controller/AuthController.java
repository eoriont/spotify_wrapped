package com.ethan5.controller;

import com.ethan5.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/auth")
public class AuthController {
    private AuthService service;

    @PostMapping("login")
    public String login(@RequestHeader("Authorization") String bearerToken) {
        return service.login(bearerToken);
    }
}
