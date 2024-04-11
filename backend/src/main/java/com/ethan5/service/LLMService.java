package com.ethan5.service;

import com.ethan5.dto.LLMRequest;
import com.ethan5.dto.LLMResponse;
import com.ethan5.dto.OpenAIRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LLMService {
    private final String apiKey;
    private final RestTemplate template;
    private static final String url = "https://api.openai.com/v1/chat/completions";

    public LLMService(
            @Value("${openai.api-key}") String apiKey,
            RestTemplate template
    ) {
        this.apiKey = apiKey;
        this.template = template;
    }

    public String generate(LLMRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        List<String> genres = req.genres();
        String prompt = String.format(
                "Assume I am really interested into %s, %s, and %s."
                        + "Describe how someone like me tends to act/think/dress. Limit to 40 words.",
                genres.get(0),
                genres.get(1),
                genres.get(2)
        );
        OpenAIRequest body = new OpenAIRequest(
                "gpt-3.5-turbo",
                List.of(new OpenAIRequest.Message("user", prompt))
        );
        HttpEntity<OpenAIRequest> entity = new HttpEntity<>(body, headers);

        return template
                .exchange(url, HttpMethod.POST, entity, LLMResponse.class)
                .getBody().choices().get(0).message().content();
    }
}
