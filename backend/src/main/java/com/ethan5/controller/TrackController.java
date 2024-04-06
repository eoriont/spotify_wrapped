package com.ethan5.controller;

import com.ethan5.dto.TrackInfo;
import com.ethan5.service.TrackService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/track")
public class TrackController {
    private TrackService service;

    @GetMapping("top-tracks")
    public List<TrackInfo> readTopTracks(@RequestHeader("Authorization") String authHeader) throws JsonProcessingException {
        return service.readTopTracks(authHeader);
    }
}
