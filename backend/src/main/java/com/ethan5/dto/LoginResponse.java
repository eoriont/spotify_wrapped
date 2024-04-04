package com.ethan5.dto;

public record LoginResponse(
        String country,
        String display_name,
        String email,
        String href,
        String id,
        String product,
        String type,
        String uri
) {
}
