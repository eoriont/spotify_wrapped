package com.ethan5.controller;

import com.ethan5.service.LLMService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/llm")
public class LLMController {
    private final LLMService service;

    @GetMapping("{id}")
    public String generate(@PathVariable("id") String userId) {
        return service.generate(userId);
    }
}
