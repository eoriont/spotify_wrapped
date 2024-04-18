package com.example.spotifywrappedapp.models;

import androidx.annotation.NonNull;

public class RecDTO {
    private String name;
    private String artist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " by " + artist;
    }
}
