package com.sparta.teamssc.domain.team.weekProgress.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeekProgressResponseDto {
    private Long id;
    private String name;
}
