package com.sparta.teamssc.domain.teamProject.service;

import com.sparta.teamssc.domain.teamProject.dto.TeamProjectDto;

public interface TeamProjectService {
    void createTeamProject(Long teamId, TeamProjectDto teamProjectDto);
    void updateTeamProject(Long teamId, TeamProjectDto teamProjectDto);

    void deleteTeamProject(Long teamId);
    TeamProjectDto getTeamProject(Long teamId);
}
