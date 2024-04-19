package com.example.spotifywrappedapp.models;

public class AuthDTO {
    private String email;
    private String password;
    private String bearerToken;

    public AuthDTO(String email, String password, String bearerToken) {
        this.email = email;
        this.password = password;
        this.bearerToken = bearerToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
