package com.ethan5.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"track\"")
public class Track {

    @Id
    private String id;
    private String name; // Matches the "name" field in the JSON

    @ManyToMany(mappedBy = "tracks")
    @JsonIgnore
    private Set<Playlist> playlists = new HashSet<>();

    public Track(String name, String id) {
        this.name = name;
        this.id = id;
    }

    protected Track() {
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
        playlist.getTracks().add(this);
    }

    public void removePlaylist(Playlist playlist) {
        this.playlists.remove(playlist);
        playlist.getTracks().remove(this);
    }

    public Set<Playlist> getPlaylists() {
//        return Collections.unmodifiableSet(playlists); // This makes the returned set read-only
        return playlists;
    }

    public String getId() {
        return id;
    }

    // Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
