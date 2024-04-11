package com.example.spotifywrappedapp.models;

import androidx.annotation.NonNull;


public class History {

    private Long id;
//
    private String date;

    private String userId;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    private String idx;
    private String track1Id;
    private String track2Id;
    private String track3Id;
    private String artist1Id;
    private String artist2Id;
    private String artist3Id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
//
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrack1Id() {
        return track1Id;
    }

    public void setTrack1Id(String track1Id) {
        this.track1Id = track1Id;
    }

    public String getTrack2Id() {
        return track2Id;
    }

    public void setTrack2Id(String track2Id) {
        this.track2Id = track2Id;
    }

    public String getTrack3Id() {
        return track3Id;
    }

    public void setTrack3Id(String track3Id) {
        this.track3Id = track3Id;
    }

    public String getArtist1Id() {
        return artist1Id;
    }

    public void setArtist1Id(String artist1Id) {
        this.artist1Id = artist1Id;
    }

    public String getArtist2Id() {
        return artist2Id;
    }

    public void setArtist2Id(String artist2Id) {
        this.artist2Id = artist2Id;
    }

    public String getArtist3Id() {
        return artist3Id;
    }

    public void setArtist3Id(String artist3Id) {
        this.artist3Id = artist3Id;
    }

    @NonNull
    @Override
    public String toString() {
        return getUserId() + "'s History #" + this.getIdx();
    }
}
