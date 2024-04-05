package com.ethan5.service;

import com.ethan5.dao.PlaylistRepository;
import com.ethan5.entity.Playlist;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaylistService {
    private PlaylistRepository repository;

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
