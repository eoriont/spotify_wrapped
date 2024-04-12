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

@Service
@AllArgsConstructor
public class ArtistService {
    private final ArtistRepository repository;

    private RestTemplate template;

    public Artist readArtist(String artistId) {
        return repository.findById(artistId).get();
    }

    public List<Artist> readTopArtists(String id, String bearerToken,
                                       int limit, int offset) {
        String url = String.format(
                "https://api.spotify.com/v1/me/top/artists?limit=%d&offset=%d",
                limit, offset);
        String token = bearerToken.substring("bearer ".length());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("content-type", "application/json");
        headers.add("authorization", "Bearer " + token);

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
                    .imageUrl(a.images().get(0).url())
                    .build();

            artists.add(artist);

            if (!artists.contains(artist)) {
                repository.saveAndFlush(artist);
            }
        });

        return artists;
    }
}
