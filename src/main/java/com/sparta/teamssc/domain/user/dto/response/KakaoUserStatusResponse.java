package com.sparta.teamssc.domain.user.dto.response;

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
