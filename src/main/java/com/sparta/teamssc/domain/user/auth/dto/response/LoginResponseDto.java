package com.sparta.teamssc.domain.user.auth.dto.response;

import com.sparta.teamssc.domain.user.user.entity.UserStatus;
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

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken, String username, Long periodId, String trackName, int period, UserStatus userStatus) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.periodId = periodId;
        this.trackName = trackName;
        this.period = period;
        this.userStatus = userStatus;
    }
}
