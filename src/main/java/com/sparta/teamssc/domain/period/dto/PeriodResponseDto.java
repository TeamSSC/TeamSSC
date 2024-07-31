package com.sparta.teamssc.domain.period.dto;

import com.sparta.teamssc.domain.period.entity.PeriodStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PeriodResponseDto {
    private final Long id; // trackId
    private final int period;
    private final String trackName;
    private final PeriodStatus status;

    @Builder
    public PeriodResponseDto(Long id, int period, String trackName, PeriodStatus status) {
        this.id = id;
        this.period = period;
        this.trackName = trackName;
        this.status = status;
    }
}
