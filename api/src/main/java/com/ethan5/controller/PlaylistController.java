package com.ethan5.controller;

import com.ethan5.dto.PlaylistRequest;
import com.ethan5.entity.Playlist;
import com.ethan5.service.PlaylistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ethan5.dto.UserRequest;
import com.ethan5.entity.User;
import com.ethan5.service.UserService;

@RestController
@RequestMapping("v1/user")
public class PlaylistController {
    private PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

    @PostMapping()
    public String createPlaylist(@RequestBody PlaylistRequest req) {
        return service.createPlaylist(req.id());
    }


    @GetMapping("{id}")
    public Playlist readPlaylist(@PathVariable("id") String id) {
        return service.readPlaylist(id);
    }
}
