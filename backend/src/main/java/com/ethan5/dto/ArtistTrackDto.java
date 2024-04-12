package com.ethan5.dto;

import java.util.List;

public record ArtistTrackDto(
        String id,
        String name,
        List<ImageDto> images,
        AlbumDTO album
) {
}
