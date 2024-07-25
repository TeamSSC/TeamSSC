package com.sparta.teamssc.domain.team.weekProgress.dto;

import com.sparta.teamssc.domain.team.weekProgress.entity.ProgressStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeekProgressResponseDto {
    private Long id;
    private String name;
    private ProgressStatus status;
}
