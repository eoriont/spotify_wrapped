package com.ethan5.dto;

public record AuthRequest(
        String email,
        String password,
        String bearerToken
) {
}
