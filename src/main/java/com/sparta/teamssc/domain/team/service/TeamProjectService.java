package com.sparta.teamssc.domain.team.service;

import com.sparta.teamssc.domain.team.dto.request.TeamProjectDto;

public interface TeamProjectService {
    void createTeamProject(Long teamId, TeamProjectDto teamProjectDto);
    void updateTeamProject(Long teamId, TeamProjectDto teamProjectDto);

    void deleteTeamProject(Long teamId);
    TeamProjectDto getTeamProject(Long teamId);
}
