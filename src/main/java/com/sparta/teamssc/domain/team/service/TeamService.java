package com.sparta.teamssc.domain.team.service;

import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.TeamCreateResponseDto;
import com.sparta.teamssc.domain.team.dto.response.TeamResponseDto;

public interface TeamService {
    TeamCreateResponseDto createTeam(TeamCreateRequestDto teamCreateRequestDto);
    TeamCreateResponseDto updateTeam(Long teamId, TeamCreateRequestDto teamCreateRequestDto);
    void deleteTeam(Long teamId);
}
