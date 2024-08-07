package com.sparta.teamssc.domain.team.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TeamCreateResponseDto {
    private final Long id;// 팀 아이디
    private final String leaderId;
    private final String weekProgress; // 주차 상태
    private final List<String> userEmails;
}