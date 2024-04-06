package com.ethan5.service;

import com.ethan5.dto.Artist;
import com.ethan5.dto.ArtistWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class ArtistService {
    private RestTemplate template;

    public List<Artist> readTopArtists(String authHeader) {
        String url = "https://api.spotify.com/v1/me/top/artists?limit=3";
        String token = authHeader.substring(7);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + token);

        return template
                .exchange(
                        url,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        ArtistWrapper.class
                )
                .getBody()
                .artists();
    }
}