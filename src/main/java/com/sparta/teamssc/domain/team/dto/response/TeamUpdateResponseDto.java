package com.sparta.teamssc.domain.team.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamUpdateResponseDto {
    private Long id;
    private String name;
    private String leaderId;
    private String teamInfo;
}