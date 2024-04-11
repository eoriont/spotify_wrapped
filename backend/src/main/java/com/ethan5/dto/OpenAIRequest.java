package com.ethan5.dto;

import java.util.List;

public record OpenAIRequest(
        String model,
        List<Message> messages
) {
    public record Message(
            String role,
            String content
    ) {
    }
}
