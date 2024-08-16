package com.sparta.teamssc.domain.period.dto;

import com.sparta.teamssc.domain.period.entity.PeriodStatus;
import lombok.Getter;

@Getter
public class PeriodRequestDto {
    private Long trackId;
//    private int period;
    private PeriodStatus status;
}
