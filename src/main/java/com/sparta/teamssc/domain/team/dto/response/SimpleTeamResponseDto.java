package com.sparta.teamssc.domain.team.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SimpleTeamResponseDto {
    private final Long id;// 팀 아이디
    private final List<String> userEmails;
}