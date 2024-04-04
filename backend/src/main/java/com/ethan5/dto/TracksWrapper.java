package com.ethan5.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TracksWrapper {
        @JsonProperty("items")
        private List<TrackInfo> tracks;

        public List<TrackInfo> getTracks() {
            return tracks;
        }

        public void setTracks(List<TrackInfo> tracks) {
            this.tracks = tracks;
        }
}
