package com.ethan5.controller;

import com.ethan5.dto.RecDTO;
import com.ethan5.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/recommendation")
public class RecommendationController {
    private final RecommendationService service;

    // in the future, make this return a list of tracks instead of a string??
    @GetMapping("{id}")
    public List<RecDTO> getRecommendations(
            @PathVariable("id") String id,
            @RequestHeader("Authorization") String bearerToken
    ) throws Exception {
        return service.getRecommendations(id, bearerToken);
    }
}
