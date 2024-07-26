package com.sparta.teamssc.domain.teamProject.service;

import com.sparta.teamssc.domain.team.entity.Team;
import com.sparta.teamssc.domain.team.repository.TeamRepository;
import com.sparta.teamssc.domain.team.service.TeamService;
import com.sparta.teamssc.domain.teamProject.dto.TeamProjectDto;
import com.sparta.teamssc.domain.teamProject.entity.TeamProject;
import com.sparta.teamssc.domain.teamProject.repository.TeamProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamProjectServiceImpl implements TeamProjectService {

    private final TeamProjectRepository teamProjectRepository;
    private final TeamService teamService;

    // 팀 프로젝트 생성하기
    @Transactional
    @Override
    public String createTeamProject(Long teamId, TeamProjectDto teamProjectDto) {
        Team team = teamService.getTeamById(teamId);

        alreadyExist(teamId);

        TeamProject teamProject = TeamProject.builder()
                .team(team)
                .projectIntro(teamProjectDto.getProjectIntro())
                .notionLink(teamProjectDto.getNotionLink())
                .gitLink(teamProjectDto.getGitLink())
                .figmaLink(teamProjectDto.getFigmaLink())
                .build();

        team.addTeamProject(teamProject);
        teamProjectRepository.save(teamProject);
        return "팀페이지 작성에 성공했습니다.";
    }

    @Override
    public void alreadyExist(Long teamId) {
        Team team = teamService.getTeamById(teamId);
        if (team.getTeamProject() != null) {
            throw new RuntimeException("팀 프로젝트가 이미 존재합니다.");
        }
    }
}