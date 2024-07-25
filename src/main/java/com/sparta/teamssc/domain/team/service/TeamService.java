package com.sparta.teamssc.domain.team.service;

import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.SimpleTeamResponseDto;
import com.sparta.teamssc.domain.team.dto.response.TeamCreateResponseDto;
import com.sparta.teamssc.domain.team.dto.response.TeamResponseDto;
import com.sparta.teamssc.domain.team.entity.Team;

import java.util.List;

public interface TeamService {
    TeamCreateResponseDto createTeam(Long weekProgressId, TeamCreateRequestDto teamCreateRequestDto);

    TeamCreateResponseDto updateTeam(Long weekProgressId, Long teamId, TeamCreateRequestDto teamCreateRequestDto);

    void deleteTeam(Long weekProgressId, Long teamId);

    List<SimpleTeamResponseDto> getAllTeams(Long weekProgressId);
    Team getTeamById(Long teamId);

    SimpleTeamResponseDto getTeamUsers(Long teamId);
}
