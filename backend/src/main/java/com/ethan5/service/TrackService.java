package com.ethan5.service;

import com.ethan5.dao.TrackRepository;
import com.ethan5.dto.TrackInfo;
import com.ethan5.dto.TracksWrapper;
import com.ethan5.entity.Track;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class TrackService {
    private TrackRepository repository;
    private RestTemplate template;

    public List<TrackInfo> readTopTracks(String id, String authHeader) throws JsonProcessingException {
        String url = "https://api.spotify.com/v1/me/top/tracks?limit=3";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authHeader.substring(7));

        TracksWrapper res = template.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), TracksWrapper.class).getBody();

        res.getTracks().forEach(t -> {
            Track track = Track.builder().name(t.name()).id(t.id()).build();

            if (repository.findById(t.id()).isEmpty()) {
                repository.save(track);
            }
        });

        return res.getTracks();
    }
}
