package com.sparta.teamssc.domain.team.weekProgress.dto;

import com.sparta.teamssc.domain.team.weekProgress.entity.ProgressStatus;
import lombok.Getter;

@Getter
public class WeekProgressUpdateRequestDto {
    private String name;
    private ProgressStatus status;
}
