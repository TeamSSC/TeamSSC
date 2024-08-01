package com.sparta.teamssc.domain.team.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class TeamMemberResponseDto {
    // 주차명
    private String currentWeekProgress;

    // 팀명
    private String teamName;
    private List<Long> userIds;
    private List<String> userNames;
}
