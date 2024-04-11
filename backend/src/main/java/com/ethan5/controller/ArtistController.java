package com.ethan5.controller;

import com.ethan5.entity.Artist;
import com.ethan5.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("v1/artist")
public class ArtistController {
    private final ArtistService service;

    @GetMapping("{id}/top-artists")
    public List<Artist> readTopArtists(
            @PathVariable("id") String id,
            @RequestHeader("Authorization") String bearerToken
    ) {
        return service.readTopArtists(id, bearerToken, 3, 0);
    }
}
