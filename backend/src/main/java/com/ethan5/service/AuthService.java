package com.ethan5.service;

import com.ethan5.dto.LoginResponse;
import com.ethan5.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {
    private UserService service;
    private RestTemplate template;

    public String login(String bearerToken) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", bearerToken);

        LoginResponse res = template.exchange(
                "https://api.spotify.com/v1/me",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LoginResponse.class
        ).getBody();

        User user = service.readUser(res.id());

        if (user == null) {
            return service.createUser(res.id());
        }

        return user.getId();
    }
}
