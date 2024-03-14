package com.ethan5.dto;

import java.util.List;

public class TracksResponse {
    private List<Track> items; // Matches the "items" field in the JSON

    // Getter and Setter
    public List<Track> getItems() {
        return items;
    }

    public void setItems(List<Track> items) {
        this.items = items;
    }
}
