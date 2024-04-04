package com.ethan5.controller;

import com.ethan5.service.PlaylistService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/playlist")
public class PlaylistController {
    private PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

//    @PostMapping()
//    public String createPlaylist(@RequestBody PlaylistRequest req) {
//        return service.createPlaylist(req.id());
//    }


//    @GetMapping("{id}")
//    public Playlist readPlaylist(@PathVariable("id") String id) {
//        return service.readPlaylist(id);
//    }


}
