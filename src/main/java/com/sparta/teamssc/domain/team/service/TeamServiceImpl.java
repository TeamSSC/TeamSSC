package com.sparta.teamssc.domain.team.service;

import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.period.service.PeriodService;
import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.TeamCreateResponseDto;
import com.sparta.teamssc.domain.team.dto.response.TeamResponseDto;
import com.sparta.teamssc.domain.team.entity.Team;
import com.sparta.teamssc.domain.team.exception.TeamCreationFailedException;
import com.sparta.teamssc.domain.team.exception.TeamNotFoundException;
import com.sparta.teamssc.domain.team.repository.TeamRepository;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final UserService userService;
    private final PeriodService periodService;

    /**
     * 팀생성
     *
     * @param teamCreateRequestDto
     * @return TeamCreateResponseDto
     */
    @Override
    public TeamCreateResponseDto createTeam(TeamCreateRequestDto teamCreateRequestDto) {
        try {
            // Period 조회
            Period period = periodService.getPeriodById(teamCreateRequestDto.getPeriodId());

            // User 조회
            List<User> users = teamCreateRequestDto.getUserEmails().stream()
                    .map(userService::getUserByEmail)
                    .collect(Collectors.toList());

            // Team 생성
            Team team = Team.builder()
                    .period(period)
                    .teamName("팀 이름")
                    .section(teamCreateRequestDto.getSection())
                    .leaderId(null)
                    .teamDescription("팀 설명")
                    .build();

            team.addUsers(users);
            teamRepository.save(team);

            // 응답 DTO 생성
            TeamCreateResponseDto teamResponseDto = TeamCreateResponseDto.builder()
                    .id(team.getId())
                    .leaderId(team.getLeaderId())
                    .users(users.stream().map(User::getEmail).collect(Collectors.toList()))
                    .build();

            return teamResponseDto;

        } catch (TeamNotFoundException e) {
            throw new TeamCreationFailedException("팀 생성에 실패했습니다.");
        }
    }

    /**
     * 팀 수정하기
     *
     * @param teamId
     * @param teamCreateRequestDto
     * @return TeamCreateResponseDto
     */
    @Override
    public TeamCreateResponseDto updateTeam(Long teamId, TeamCreateRequestDto teamCreateRequestDto) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("팀을 찾을 수 없습니다."));

        Period period = periodService.getPeriodById(teamCreateRequestDto.getPeriodId());
        List<User> users = teamCreateRequestDto.getUserEmails().stream()
                .map(userService::getUserByEmail)
                .collect(Collectors.toList());

        team.updatePeriod(period);
        team.updateSection(teamCreateRequestDto.getSection());
        team.updateUsers(users);

        teamRepository.save(team);

        return TeamCreateResponseDto.builder()
                .id(team.getId())
                .leaderId(team.getLeaderId())
                .users(users.stream().map(User::getEmail).collect(Collectors.toList()))
                .build();
    }

    /**
     * 팀삭제하기
     *
     * @param teamId
     */
    @Transactional
    @Override
    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("팀을 찾을 수 없습니다."));
        teamRepository.delete(team);
    }

    /**
     * 팀 조회하기
     *
     * @return List<TeamResponseDto></TeamResponseDto>
     */
    @Transactional(readOnly = true)
    @Override
    public List<TeamResponseDto> getAllTeams() {
        try {
            List<Team> teams = teamRepository.findAll();
            return teams.stream()
                    .map(team -> TeamResponseDto.builder()
                            .id(team.getId())
                            .teamName(team.getTeamName())
                            .leaderId(team.getLeaderId())
                            .users(team.getUsers().stream().map(User::getEmail).collect(Collectors.toList()))
                            .build())
                    .collect(Collectors.toList());
        } catch (TeamNotFoundException e) {
            throw new TeamNotFoundException("팀을 찾을 수 없습니다.");
        }
    }
}