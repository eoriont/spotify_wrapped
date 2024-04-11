package com.ethan5.controller;

import com.ethan5.dto.LLMRequest;
import com.ethan5.service.LLMService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/llm")
public class LLMController {
    private final LLMService service;

    @GetMapping("{genre1}/{genre2}/{genre3}")
    public String generate(@PathVariable("genre1") String genre1,
                           @PathVariable("genre2") String genre2,
                           @PathVariable("genre3") String genre3) {
        return service.generate(new LLMRequest(List.of(genre1, genre2, genre3)));
    }
}
