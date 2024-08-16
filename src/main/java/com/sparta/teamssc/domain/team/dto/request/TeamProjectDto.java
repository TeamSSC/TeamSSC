package com.sparta.teamssc.domain.team.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamProjectDto {
    private String projectIntro;
    private String notionLink;
    private String gitLink;
    private String figmaLink;
}
