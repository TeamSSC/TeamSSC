package com.sparta.teamssc.domain.team.service;

import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.*;
import com.sparta.teamssc.domain.team.entity.Team;

import java.util.List;

public interface TeamService {
    TeamCreateResponseDto createTeam(Long weekProgressId, TeamCreateRequestDto teamCreateRequestDto);

    TeamCreateResponseDto updateTeam(Long weekProgressId, Long teamId, TeamCreateRequestDto teamCreateRequestDto);

    void deleteTeam(Long weekProgressId, Long teamId);

    List<SimpleTeamResponseDto> getAllTeams(Long weekProgressId);
    Team getTeamById(Long teamId); //전체 팀 라인업
    List<SimpleTeamNameResponseDto> getMyTeams(Long userId); // 내가 속한 팀
    TeamMemberResponseDto getMyTeamMembers(Long teamId, Long userId); //내가 속한 팀의 팀원들

    SimpleTeamResponseDto getTeamUsers(Long teamId);

    boolean isUserInTeam(Long userId, Long teamId);
}
