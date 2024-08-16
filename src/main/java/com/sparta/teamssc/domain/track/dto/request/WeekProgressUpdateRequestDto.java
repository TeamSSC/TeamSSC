package com.sparta.teamssc.domain.track.dto.request;

import com.sparta.teamssc.domain.track.entity.ProgressStatus;
import lombok.Getter;

@Getter
public class WeekProgressUpdateRequestDto {
    private ProgressStatus status;
}
