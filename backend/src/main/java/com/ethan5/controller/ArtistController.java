package com.ethan5.controller;

import com.ethan5.dto.ArtistInfo;
import com.ethan5.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("v1/artist")
public class ArtistController {
    private final ArtistService service;

    @GetMapping("top-artists")
    public List<ArtistInfo> readTopArtists(@RequestHeader("Authorization") String authHeader) {
        return service.readTopArtists(authHeader);
    }
}
