package com.sparta.teamssc.domain.weekProgress.dto;

import com.sparta.teamssc.domain.weekProgress.entity.ProgressStatus;
import lombok.Getter;

@Getter
public class WeekProgressUpdateRequestDto {
    private ProgressStatus status;
}
