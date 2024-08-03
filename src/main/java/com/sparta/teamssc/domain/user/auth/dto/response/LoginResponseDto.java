package com.sparta.teamssc.domain.user.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final String accessToken;
    private final String refreshToken;
    private final String username;
    private final Long periodId;
    private final String trackName;
    private final int period;
    private final String fcmToken;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken, String username, Long periodId, String trackName, int period, String fcmToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.periodId = periodId;
        this.trackName = trackName;
        this.period = period;
        this.fcmToken = fcmToken;
    }
}
