package com.sparta.teamssc.domain.user.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final String accessToken;
    private final String refreshToken;
    private final String username;
    private final Long periodId;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken, String username, Long periodId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.periodId = periodId;
    }
}
