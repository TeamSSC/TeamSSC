package com.sparta.teamssc.domain.team.dto.request;

import com.sparta.teamssc.domain.team.entity.Section;
import lombok.Getter;

import java.util.List;

// 주차제거 - 팀은 한 주차 안에서 수정됨
@Getter
public class TeamRequestDto {
    private Long periodId; //기수
    private Section section; //A, B 세션
    private List<String> userEmails;
}
