package com.sparta.teamssc.domain.user.dto.response;

import com.sparta.teamssc.domain.user.entity.UserStatus;
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
    private final UserStatus userStatus;
    private final String fcmToken;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken, String username, Long periodId, String trackName, int period, UserStatus userStatus, String fcmToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.periodId = periodId;
        this.trackName = trackName;
        this.period = period;
        this.userStatus = userStatus;
        this.fcmToken = fcmToken;
    }
}
