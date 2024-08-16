package com.sparta.teamssc.domain.track.dto.request;

import com.sparta.teamssc.domain.track.entity.PeriodStatus;
import lombok.Getter;

@Getter
public class PeriodRequestDto {
    private Long trackId;
//    private int period;
    private PeriodStatus status;
}
