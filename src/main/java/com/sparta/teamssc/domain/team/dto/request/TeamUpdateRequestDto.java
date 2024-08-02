package com.sparta.teamssc.domain.team.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TeamUpdateRequestDto {
    private String name;
    private Long leaderId;
    private String teamInfo;
}

