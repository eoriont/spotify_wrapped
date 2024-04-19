package com.ethan5.dto;

public record UpdateUserRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
