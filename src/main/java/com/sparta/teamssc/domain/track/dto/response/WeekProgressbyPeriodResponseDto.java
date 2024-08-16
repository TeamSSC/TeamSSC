package com.sparta.teamssc.domain.track.dto.response;

import com.sparta.teamssc.domain.track.entity.ProgressStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeekProgressbyPeriodResponseDto {
    private Long id;
    private String name;
    private ProgressStatus status;
}
