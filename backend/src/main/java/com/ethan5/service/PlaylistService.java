package com.ethan5.service;

import com.ethan5.dao.PlaylistRepository;
import com.ethan5.dao.TrackRepository;
import com.ethan5.entity.Playlist;
import com.ethan5.entity.Track;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaylistService {
    private PlaylistRepository repository;
    private UserService userService;
    private TrackRepository trackRepository;


    public PlaylistService(PlaylistRepository repository, UserService userService, TrackRepository trackRepository) {
        this.repository = repository;
        this.userService = userService;
        this.trackRepository = trackRepository;
    }

//    public String createPlaylist(String id) {
//        Playlist playlist = new Playlist(id);
//        repository.saveAndFlush(playlist);
//        return playlist.getId();
//    }

    public void createPlaylist(Playlist p) {
        repository.saveAndFlush(p);
    }

    public Playlist readPlaylist(String id) {
        return repository.findById(id).orElse(null);
    }

}
