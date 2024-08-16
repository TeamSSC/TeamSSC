package com.sparta.teamssc.domain.track.dto.request;

import com.sparta.teamssc.domain.track.entity.PeriodStatus;
import lombok.Getter;

@Getter
public class PeriodUpdateRequestDto {

    private PeriodStatus status;
}
