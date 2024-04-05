package com.ethan5.service;

import com.ethan5.dao.TrackRepository;
import com.ethan5.dto.TrackInfo;
import com.ethan5.dto.TracksWrapper;
import com.ethan5.entity.Track;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class TrackService {
    private TrackRepository repository;
    private RestTemplate template;

    public Set<Track> readTopTracks(String authHeader) throws JsonProcessingException {
        // TODO make some config for endpoints
        String url = "https://api.spotify.com/v1/me/top/tracks";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authHeader.substring(7));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> rawJson = template.exchange(url, HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        TracksWrapper wrapper = mapper.readValue(rawJson.getBody(), TracksWrapper.class);
        Set<Track> tracks = new HashSet<>();

        for (TrackInfo track : wrapper.getTracks()) {
            Track newTrack = Track.builder().name(track.getName()).id(track.getId()).build();
            tracks.add(newTrack);

            if (repository.findById(track.getId()).isEmpty()) {
                repository.save(newTrack);
            }
        }

        return tracks;

    }
}
