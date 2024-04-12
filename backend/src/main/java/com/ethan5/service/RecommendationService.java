package com.ethan5.service;

import com.ethan5.dto.RecDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationService {
    private final TrackService trackService;
    private final RestTemplate template;

    public List<RecDTO> getRecommendations(String id, String bearerToken) {
        String apiUrl = "https://colbyb1123.pythonanywhere.com/";
        System.out.println(bearerToken);
        String queryStr = trackService
                .readTopTracks(id, "Bearer " + bearerToken, 3, 0)
                .stream()
                .map(t -> String.format("data=%s", t.getName()))
                .collect(Collectors.joining("&"));
        String url = String.format("%s?%s", apiUrl, queryStr);

        // The first item is the reference song,
        // the rest are the recommendations
        // if this actually returns a string "No Input" there was
        // an error on the backend
        List<RecDTO> list = template.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RecDTO>>() {}
        ).getBody();

        System.out.println(list);
        return list;
    }
}
