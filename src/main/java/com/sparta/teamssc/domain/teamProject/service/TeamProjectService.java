package com.sparta.teamssc.domain.teamProject.service;

import com.sparta.teamssc.domain.teamProject.dto.TeamProjectDto;

public interface TeamProjectService {
    String createTeamProject(Long teamId, TeamProjectDto teamProjectDto);
    void alreadyExist(Long teamId);
}
