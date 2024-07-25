package com.sparta.teamssc.domain.team.service;

import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.period.service.PeriodService;
import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.TeamCreateResponseDto;
import com.sparta.teamssc.domain.team.entity.Team;
import com.sparta.teamssc.domain.team.exception.TeamCreationFailedException;
import com.sparta.teamssc.domain.team.exception.TeamNotFoundException;
import com.sparta.teamssc.domain.team.repository.TeamRepository;
import com.sparta.teamssc.domain.team.weekProgress.entity.WeekProgress;
import com.sparta.teamssc.domain.team.weekProgress.service.WeekProgressService;
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
    private final WeekProgressService weekProgressService;

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

            // WeekProgress 조회
            WeekProgress weekProgress = weekProgressService.getWeekProgressById(teamCreateRequestDto.getWeekProgressId());

            // User 조회
            List<User> users = teamCreateRequestDto.getUserEmails().stream()
                    .map(userService::getUserByEmail)
                    .collect(Collectors.toList());

            // Team 생성
            Team team = Team.builder()
                    .period(period)
                    .section(teamCreateRequestDto.getSection())
                    .weekProgress(weekProgress)
                    .leaderId(null)
                    .teamDescription("팀 설명")
                    .build();

            team.addUsers(users);
            teamRepository.save(team);

            // 응답 DTO 생성
            TeamCreateResponseDto teamResponseDto = TeamCreateResponseDto.builder()
                    .id(team.getId())
                    .leaderId(team.getLeaderId())
                    .weekProgress(team.getWeekProgress().getName())
                    .userEmails(users.stream().map(User::getEmail).collect(Collectors.toList()))
                    .build();

            return teamResponseDto;

        } catch (Exception e) {
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
        Team team = getTeamById(teamId);

        Period period = periodService.getPeriodById(teamCreateRequestDto.getPeriodId());

        WeekProgress weekProgress = weekProgressService.getWeekProgressById(teamCreateRequestDto.getWeekProgressId());
        List<User> users = teamCreateRequestDto.getUserEmails().stream()
                .map(userService::getUserByEmail)
                .collect(Collectors.toList());

        team.updatePeriod(period);
        team.updateSection(teamCreateRequestDto.getSection());
        team.updateWeekProgress(weekProgress);
        team.updateUsers(users);

        teamRepository.save(team);

        return TeamCreateResponseDto.builder()
                .id(team.getId())
                .leaderId(team.getLeaderId())
                .weekProgress(team.getWeekProgress().getName())
                .userEmails(users.stream().map(User::getEmail).collect(Collectors.toList()))
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
      Team team = getTeamById(teamId);
        teamRepository.delete(team);
    }

    /**
     * 팀 조회하기
     *
     * @return List<TeamResponseDto>
     */
    /**
     * 팀 조회하기
     *
     * @return List<TeamCreateResponseDto>
     */
    @Transactional(readOnly = true)
    @Override
    public List<TeamCreateResponseDto> getAllTeams(Long weekProgressId) {
        List<Team> teams = teamRepository.findAllByWeekProgressId(weekProgressId);
        return teams.stream()
                .map(team -> TeamCreateResponseDto.builder()
                        .id(team.getId())
                        .leaderId(team.getLeaderId())
                        .weekProgress(team.getWeekProgress().getName())
                        .userEmails(team.getUsers().stream()
                                .map(User::getEmail)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
    @Override
    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("팀을 찾을 수 없습니다."));
    }
}