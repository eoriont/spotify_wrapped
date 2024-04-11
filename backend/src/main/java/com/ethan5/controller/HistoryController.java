package com.ethan5.controller;

import com.ethan5.entity.History;
import com.ethan5.service.HistoryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/history")
public class HistoryController {
    private final HistoryService service;

    @GetMapping("{userId}")
    public List<History> readHistory(@RequestHeader("Authorization") String bearerToken, @PathVariable("userId") String userId) {
        return service.readHistory(userId, bearerToken);
    }

}
