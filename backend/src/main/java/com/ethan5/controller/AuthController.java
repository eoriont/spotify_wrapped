package com.ethan5.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ethan5.dto.AuthRequest;
import com.ethan5.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("v1/auth")
public class AuthController {
    private AuthService service;

    @PostMapping("login")
    public String login(@RequestBody AuthRequest req) {
        return service.login(req);
    }
}
