package com.sparta.teamssc.domain.period.dto;

import com.sparta.teamssc.domain.period.entity.PeriodStatus;
import lombok.Getter;

@Getter
public class PeriodUpdateRequestDto {

    private PeriodStatus status;
}
