package com.ethan5.dto;

import java.util.List;

public record LLMResponse(
        List<LLMChoice> choices
) {
    public record LLMChoice(Message message) {
        public record Message(String content) {
        }
    }
}
