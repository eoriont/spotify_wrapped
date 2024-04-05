package com.ethan5.controller;

import com.ethan5.service.PlaylistService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/playlist")
public class PlaylistController {
    private PlaylistService service;

//    @PostMapping("{id}")
//    public String createPlaylist(@PathVariable("id") String id) {
//        return service.createPlaylist(id);
//    }


//    @GetMapping("{id}")
//    public Playlist readPlaylist(@PathVariable("id") String id) {
//        return service.readPlaylist(id);
//    }
}
