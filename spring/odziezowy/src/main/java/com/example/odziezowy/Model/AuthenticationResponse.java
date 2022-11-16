package com.example.odziezowy.Model;

public class AuthenticationResponse {

    private final String token;
    private final Long userId;
    private final Integer active;

    public AuthenticationResponse(String token, Long userId, Integer active) {
        this.token = token;
        this.userId = userId;
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public Integer getActive() {
        return active;
    }
    public Long getUserId() {
        return userId;
    }
}