package com.sparta.teamssc.domain.team.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamResponseDto {
    private final Long id;// 팀 아이디
    private final String teamName;
}
