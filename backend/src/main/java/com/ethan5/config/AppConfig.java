package com.ethan5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }

    // TODO: We can worry about making this more secure later
    public static String SPOTIFY_CLIENT_ID = "57630b7e960946ab83c1e0dbda46a4ca";
    public static String SPOTIFY_CLIENT_SECRET = "";
    public static String REDIRECT_URI = "http://localhost:8080/";
}
