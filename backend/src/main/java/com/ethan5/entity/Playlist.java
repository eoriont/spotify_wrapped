package com.ethan5.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "\"playlist\"")
public class Playlist {
    @Id
    private String id;
    private String userId;
    private List<String> tracks;


    public Playlist(String id) {
        this.id = id;
    }

    public List<String> getTracks() {
        return tracks;
    }

    public void setTracks(List<String> tracks) {
        this.tracks = tracks;
    }

    public String getId() {
        return id;
    }
}
