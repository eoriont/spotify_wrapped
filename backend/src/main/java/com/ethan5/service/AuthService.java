package com.ethan5.service;

import com.ethan5.dto.AuthRequest;
import com.ethan5.dto.LoginResponse;
import com.ethan5.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {
    private UserService service;
    private BCryptPasswordEncoder encoder;
    private RestTemplate template;

    public String login(AuthRequest req) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", req.bearerToken());

        LoginResponse res = template.exchange(
                "https://api.spotify.com/v1/me",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LoginResponse.class
        ).getBody();

        User user = service.readUserByEmail(req.email());

        if (user == null) {
            user = User.builder()
                        .id(res.id())
                        .email(req.email())
                        .password(encoder.encode(req.password()))
                        .build();

            return service.createUser(user);
        }

        boolean match = encoder.matches(req.password(), user.getPassword())
                && user.getId().equals(res.id());

        if (!match) {
            return null;
        }

        return user.getId();
    }
}
