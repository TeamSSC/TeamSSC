package com.sparta.teamssc.domain.user.auth.dto.response;

import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoUserStatusResponse {

    private final Long periodId;
    private final String trackName;
    private final int period;

    @Builder
    public KakaoUserStatusResponse(Long periodId, String trackName, int period) {
        this.periodId = periodId;
        this.trackName = trackName;
        this.period = period;
    }
}
