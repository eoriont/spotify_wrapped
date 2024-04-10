package com.ethan5.service;

import com.ethan5.dao.TrackRepository;
import com.ethan5.dto.TracksWrapper;
import com.ethan5.entity.Track;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TrackService {
    private final TrackRepository repository;
    private final HistoryService historyService;
    private RestTemplate template;

    public List<Track> readTopTracks(String id, String bearerToken) {
        String url = "https://api.spotify.com/v1/me/top/tracks?limit=3";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken.substring("Bearer ".length()));

        TracksWrapper res = template
                .exchange(
                        url,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        TracksWrapper.class)
                .getBody();

        log.info("Res: {}", res.toString());

        List<Track> tracks = new ArrayList<>();

        res.tracks().forEach(t -> {
            Track track = Track
                    .builder()
                    .name(t.name())
                    .id(t.id())
//                    .imageUrl(t.images().get(0).url())
                    .build();

            if (repository.findById(t.id()).isEmpty()) {
                repository.save(track);
            }

            tracks.add(track);
        });

        historyService.createHistory(id, Optional.of(tracks), Optional.empty());
        return tracks;
    }
}
