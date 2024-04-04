package com.ethan5.controller;

import com.ethan5.entity.Playlist;
import com.ethan5.entity.User;
import com.ethan5.service.PlaylistService;
import com.ethan5.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/user")
public class UserController {
    private PlaylistService playlistService;
    private UserService userService;

    public UserController(PlaylistService service, UserService userService) {
        this.playlistService = service;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") String id) {
        return userService.readUser(id);
    }

    @GetMapping("{id}/topsongs")
    public Playlist readPlaylist(@PathVariable("id") String id, @RequestHeader("Authorization") String authHeader) {
        Playlist p = userService.readUser(id).getPlaylist();
        // How often should we look at the API?
        boolean shouldRefresh = true; // p == null
        if (shouldRefresh) {
            p = userService.getUserTopTracks(id, authHeader);
        }
        return p;
    }

}
