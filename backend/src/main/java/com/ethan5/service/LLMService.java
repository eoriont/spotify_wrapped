package com.ethan5.service;

import com.ethan5.dto.LLMResponse;
import com.ethan5.dto.OpenAIRequest;
import com.ethan5.entity.History;
import com.ethan5.entity.Track;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class LLMService {
    private final RestTemplate template;
    private static final String URL =
            "https://api.openai.com/v1/chat/completions";

    private HistoryService historyService;
    private TrackService trackService;

//    @Value("${openai.api-key}")
//    private String apiKey;

    private Environment env;

    public String generate(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(env.getProperty("openai.api-key"));

        History h = historyService.getLatest(userId);
        Track t1 = trackService.readTrack(h.getTrack1Id());
        Track t2 = trackService.readTrack(h.getTrack2Id());
        Track t3 = trackService.readTrack(h.getTrack3Id());

        String prompt = String.format(
                "Here are the titles of 3 of my favorite songs: %s, %s, and %s."
                        + "Based on the supposed genre of these songs, describe how someone like me tends to "
                        + "act/think/dress. Limit to 40 words, and respond as if you were provided the genres"
                        + " instead of the songs.",
                t1.getName(), t2.getName(), t3.getName()
        );
        OpenAIRequest body = new OpenAIRequest(
                "gpt-3.5-turbo",
                List.of(new OpenAIRequest.Message("user", prompt))
        );
        HttpEntity<OpenAIRequest> entity = new HttpEntity<>(body, headers);

        return template
                .exchange(URL, HttpMethod.POST, entity, LLMResponse.class)
                .getBody().choices().get(0).message().content();
    }
}
