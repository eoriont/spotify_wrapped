package com.ethan5.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {
    @Value("${spotify.client.id}")
    public String clientId;

    @Value("${spotify.client.secret}")
    public String clientSecret;

    @Value("${spotify.redirect.uri}")
    public String redirectUri;

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }

    @Bean
    public String clientId() {
        return clientId;
    }

    @Bean
    public String clientSecret() {
        return clientSecret;
    }

    @Bean
    public String redirectUri() {
        return redirectUri;
    }
}
