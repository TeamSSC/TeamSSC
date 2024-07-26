package com.sparta.teamssc.domain.weekProgress.dto;

import com.sparta.teamssc.domain.weekProgress.entity.ProgressStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeekProgressResponseDto {
    private Long id;
    private String name;
    private ProgressStatus status;
}
