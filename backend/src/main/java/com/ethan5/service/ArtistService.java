package com.ethan5.service;

import com.ethan5.dao.ArtistRepository;
import com.ethan5.dto.ArtistTrackDto;
import com.ethan5.dto.ArtistWrapper;
import com.ethan5.entity.Artist;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArtistService {
    private final ArtistRepository repository;
    private final HistoryService historyService;
    private RestTemplate template;

    public List<Artist> readTopArtists(String id, String bearerToken) {
        String url = "https://api.spotify.com/v1/me/top/artists?limit=3";
        String token = bearerToken.substring(7);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + token);

        List<ArtistTrackDto> topArtists = template
                .exchange(
                        url,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        ArtistWrapper.class
                )
                .getBody()
                .artists();

        List<Artist> artists = new ArrayList<>();

        topArtists.forEach(a -> {
            Artist artist = Artist
                    .builder()
                    .id(a.id())
                    .name(a.name())
                    .userId(id)
                    .build();

            repository.save(artist);
            artists.add(artist);
        });

        historyService.createHistory(id, Optional.empty(), Optional.of(artists));
        return artists;
    }
}