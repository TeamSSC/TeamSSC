package com.sparta.teamssc.domain.track.dto.response;

import com.sparta.teamssc.domain.track.entity.ProgressStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeekProgressResponseDto {
    private Long id;
    private Long periodId;
    private String name;
    private ProgressStatus status;
}
