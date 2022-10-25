package com.example.odziezowy.Model;

public class AuthenticationResponse {

    private String token;
    private Long userId;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }
}