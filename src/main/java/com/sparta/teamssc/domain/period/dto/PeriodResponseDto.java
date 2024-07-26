package com.sparta.teamssc.domain.period.dto;

import com.sparta.teamssc.domain.period.entity.PeriodStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PeriodResponseDto {
    private final Long id; // trackId
    private final int period;
    private final PeriodStatus status;

    @Builder
    public PeriodResponseDto(Long id, int period, PeriodStatus status) {
        this.id = id;
        this.period = period;
        this.status = status;
    }
}