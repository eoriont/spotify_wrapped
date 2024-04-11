package com.ethan5.controller;

import com.ethan5.entity.History;
import com.ethan5.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/history")
public class HistoryController {
    private final HistoryService service;

    @GetMapping("{userId}")
    public List<History> readHistory(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("userId") String userId) {
        return service.readHistory(userId, bearerToken);
    }

}
