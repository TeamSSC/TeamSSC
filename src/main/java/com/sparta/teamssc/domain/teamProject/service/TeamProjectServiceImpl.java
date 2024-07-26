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
    @Override
    @Transactional
    public void createTeamProject(Long teamId, TeamProjectDto teamProjectDto) {
        Team team = teamService.getTeamById(teamId);

        if (team.getTeamProject() != null) {
            throw new RuntimeException("팀 프로젝트가 이미 존재합니다.");
        }

        TeamProject teamProject = TeamProject.builder()
                .team(team)
                .projectIntro(teamProjectDto.getProjectIntro())
                .notionLink(teamProjectDto.getNotionLink())
                .gitLink(teamProjectDto.getGitLink())
                .figmaLink(teamProjectDto.getFigmaLink())
                .build();

        team.addTeamProject(teamProject);
        teamProjectRepository.save(teamProject);
    }

    // 수정
    @Override
    @Transactional
    public void updateTeamProject(Long teamId, TeamProjectDto teamProjectDto) {

        TeamProject teamProject = isExistTeamProject(teamId);

        teamProject.updateProjectIntro(teamProjectDto.getProjectIntro());
        teamProject.updateNotionLink(teamProjectDto.getNotionLink());
        teamProject.updateGitLink(teamProjectDto.getGitLink());
        teamProject.updateFigmaLink(teamProjectDto.getFigmaLink());

        teamProjectRepository.save(teamProject);
    }

    // 팀프로젝트 삭제
    @Override
    @Transactional
    public void deleteTeamProject(Long teamId) {
        TeamProject teamProject = isExistTeamProject(teamId);

        teamProject.delete();
        teamProjectRepository.save(teamProject);
    }

    @Override
    public TeamProjectDto getTeamProject(Long teamId) {
        return null;
    }

    // 찾을 수 없는 프로젝트
   private TeamProject isExistTeamProject(Long teamId) {

       Team team = teamService.getTeamById(teamId);
       if (team.getTeamProject() == null) {
           throw new RuntimeException("팀 프로젝트가 없습니다.");
       }
       return team.getTeamProject();
   }

    @Override
    public void alreadyExist(Long teamId) {
        Team team = teamService.getTeamById(teamId);
        if (team.getTeamProject() != null) {
            throw new RuntimeException("팀 프로젝트가 이미 존재합니다.");
        }
    }
}