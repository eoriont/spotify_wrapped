package com.ethan5.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationService {
    private final TrackService trackService;
    private final RestTemplate template;

    public String getRecommendations(String id, String bearerToken) throws Exception {
        String apiUrl = "https://colbyb1123.pythonanywhere.com/";
        String queryStr = trackService
                .readTopTracks(id, bearerToken)
                .stream()
                .map(t -> String.format("data=%s", t.getName()))
                .collect(Collectors.joining("&"));
        String url = String.format("%s?%s", apiUrl, queryStr);

        return template.exchange(url, HttpMethod.GET, null, String.class).getBody();
    }
}
