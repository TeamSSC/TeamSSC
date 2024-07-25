package com.sparta.teamssc.domain.team.dto.request;

import com.sparta.teamssc.domain.team.entity.Section;

import com.sparta.teamssc.domain.team.entity.WeekProgress;
import lombok.Getter;

import java.util.List;

// 팀생성 Resposne
@Getter
public class TeamCreateRequestDto {
    private Long periodId; // 기수
    private Section section; // 세션
    private WeekProgress weekProgress; // 주차 상태
    private List<String> userEmails;
}