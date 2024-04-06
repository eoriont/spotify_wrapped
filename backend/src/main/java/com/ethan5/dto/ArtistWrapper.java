package com.ethan5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ArtistWrapper(
        @JsonProperty("items") List<ArtistInfo> artists
) {
}
