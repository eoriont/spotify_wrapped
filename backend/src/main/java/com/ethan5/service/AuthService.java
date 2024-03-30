package com.ethan5.service;

import com.ethan5.dto.LoginResponse;
import com.ethan5.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
    private RestTemplate template;
    private UserService service;

    public AuthService(RestTemplate template, UserService service) {
        this.template = template;
        this.service = service;
    }

    public void login(String token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + token);
        LoginResponse res = template.exchange("https://api.spotify.com/v1/me",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        LoginResponse.class)
                .getBody();

        User user = service.readUser(res.id());

        if (user == null) {
            service.createUser(res.id());
        }
    }
}
