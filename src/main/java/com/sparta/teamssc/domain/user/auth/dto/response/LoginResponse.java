package com.sparta.teamssc.domain.user.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final String accessToken;
    private final String refreshToken;
    private final String username;

    @Builder
    public LoginResponse(String accessToken, String refreshToken, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
    }
}
