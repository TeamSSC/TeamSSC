package com.sparta.teamssc.domain.teamProject.service;

import com.amazonaws.services.kms.model.AlreadyExistsException;
import com.sparta.teamssc.domain.team.entity.Team;
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
    @Override
    @Transactional
    public void createTeamProject(Long teamId, TeamProjectDto teamProjectDto) {
        try {
            Team team = teamService.getTeamById(teamId);

            TeamProject teamProject = TeamProject.builder()
                    .team(team)
                    .projectIntro(teamProjectDto.getProjectIntro())
                    .notionLink(teamProjectDto.getNotionLink())
                    .gitLink(teamProjectDto.getGitLink())
                    .figmaLink(teamProjectDto.getFigmaLink())
                    .build();

            team.addTeamProject(teamProject);
            teamProjectRepository.save(teamProject);
        } catch (AlreadyExistsException e) {
            throw new IllegalArgumentException("팀 프로젝트가 이미 존재합니다.");
        }
    }

    // 수정
    @Override
    @Transactional
    public void updateTeamProject(Long teamId, TeamProjectDto teamProjectDto) {
        try {
            TeamProject teamProject = isExistTeamProject(teamId);

            teamProject.updateProjectIntro(teamProjectDto.getProjectIntro());
            teamProject.updateNotionLink(teamProjectDto.getNotionLink());
            teamProject.updateGitLink(teamProjectDto.getGitLink());
            teamProject.updateFigmaLink(teamProjectDto.getFigmaLink());

            teamProjectRepository.save(teamProject);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("수정할 팀 프로젝트가 없습니다.");
        }
    }

    // 팀프로젝트 삭제
    @Override
    @Transactional
    public void deleteTeamProject(Long teamId) {
        try {
            TeamProject teamProject = isExistTeamProject(teamId);

            teamProject.delete();
            teamProjectRepository.save(teamProject);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("삭제할 팀 프로젝트가 없습니다.");
        }
    }

    // 팀프로젝트 불러오기
    @Override
    @Transactional(readOnly = true)
    public TeamProjectDto getTeamProject(Long teamId) {
        try {
            TeamProject teamProject = isExistTeamProject(teamId);

            return TeamProjectDto.builder()
                    .projectIntro(teamProject.getProjectIntro())
                    .notionLink(teamProject.getNotionLink())
                    .gitLink(teamProject.getGitLink())
                    .figmaLink(teamProject.getFigmaLink())
                    .build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("팀 프로젝트를 찾을 수 없습니다.");
        }
    }

    // 찾을 수 없는 프로젝트
    private TeamProject isExistTeamProject(Long teamId) {
        Team team = teamService.getTeamById(teamId);
        TeamProject teamProject = team.getTeamProject();

        if (teamProject.isDeleted()) {
            throw new IllegalArgumentException("삭제된 팀 프로젝트입니다.");
        } else if (teamProject == null) {
            throw new IllegalArgumentException("팀 프로젝트가 없습니다.");
        }
        return teamProject;
    }
}