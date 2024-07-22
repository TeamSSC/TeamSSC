package com.sparta.teamssc.domain.user.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
