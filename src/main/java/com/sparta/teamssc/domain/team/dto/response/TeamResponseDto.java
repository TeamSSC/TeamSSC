package com.sparta.teamssc.domain.team.dto.response;

import com.sparta.teamssc.domain.user.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TeamResponseDto {
    private final Long id;// 팀 아이디
    private final String teamName;
}
