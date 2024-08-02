package com.sparta.teamssc.domain.team.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamInquiryResponseDto {
    private Long id;
    private String teamName;
    private String leaderId;
    private String leaderName;
    private String teamInfo;
}
