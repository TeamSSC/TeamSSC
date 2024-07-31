package com.sparta.teamssc.domain.team.service;

import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.period.service.PeriodService;
import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.*;
import com.sparta.teamssc.domain.team.entity.Team;
import com.sparta.teamssc.domain.team.exception.TeamCreationFailedException;
import com.sparta.teamssc.domain.team.exception.TeamNotFoundException;
import com.sparta.teamssc.domain.team.repository.TeamRepository;
import com.sparta.teamssc.domain.weekProgress.entity.WeekProgress;
import com.sparta.teamssc.domain.weekProgress.service.WeekProgressService;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import com.sparta.teamssc.domain.userTeamMatches.entity.UserTeamMatch;
import com.sparta.teamssc.domain.userTeamMatches.service.UserTeamMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final UserService userService;
    private final UserTeamMatchService userTeamMatchService;
    private final PeriodService periodService;
    private final WeekProgressService weekProgressService;

    @Transactional
    @Override
    public TeamCreateResponseDto createTeam(Long weekProgressId, TeamCreateRequestDto teamCreateRequestDto) {
        try {
            Period period = periodService.getPeriodById(teamCreateRequestDto.getPeriodId());
            WeekProgress weekProgress = weekProgressService.getWeekProgressById(weekProgressId);
            List<User> users = teamCreateRequestDto.getUserEmails().stream()
                    .map(userService::getUserByEmail)
                    .collect(Collectors.toList());

            Team team = Team.builder()
                    .period(period)
                    .section(teamCreateRequestDto.getSection())
                    .weekProgress(weekProgress)
                    .deleted(false)
                    .userTeamMatches(new ArrayList<>())
                    .build();

            team = teamRepository.save(team);
            addUserTeamMatches(team, users);

            return buildTeamCreateResponseDto(team, users);
        } catch (Exception e) {
            throw new TeamCreationFailedException("팀 생성에 실패했습니다.");
        }
    }

    @Transactional
    @Override
    public TeamCreateResponseDto updateTeam(Long weekProgressId, Long teamId, TeamCreateRequestDto teamCreateRequestDto) {
        try {
            Team team = getTeamById(teamId);
            Period period = periodService.getPeriodById(teamCreateRequestDto.getPeriodId());
            WeekProgress weekProgress = weekProgressService.getWeekProgressById(weekProgressId);
            List<User> users = teamCreateRequestDto.getUserEmails().stream()
                    .map(userService::getUserByEmail)
                    .collect(Collectors.toList());

            team.updatePeriod(period);
            team.updateSection(teamCreateRequestDto.getSection());
            team.updateWeekProgress(weekProgress);

            removeUserTeamMatches(team);
            addUserTeamMatches(team, users);

            teamRepository.save(team);

            return buildTeamCreateResponseDto(team, users);
        } catch (Exception e) {
            throw new TeamCreationFailedException("팀 수정에 실패했습니다.");
        }
    }

    @Transactional
    @Override
    public void deleteTeam(Long weekProgressId, Long teamId) {
        Team team = getTeamById(teamId);
        removeUserTeamMatches(team);
        team.delete();
        teamRepository.save(team);
    }

    @Override
    public List<SimpleTeamResponseDto> getAllTeams(Long weekProgressId) {
        List<Team> teams = teamRepository.findAllByWeekProgressIdAndNotDeleted(weekProgressId);
        return teams.stream()
                .map(team -> SimpleTeamResponseDto.builder()
                        .id(team.getId())
                        .userEmails(team.getUserTeamMatches().stream()
                                .map(userTeamMatch -> userTeamMatch.getUser().getEmail())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SimpleTeamResponseDto getTeamUsers(Long teamId) {
        Team team = getTeamById(teamId);
        List<String> userEmails = team.getUserTeamMatches().stream()
                .map(userTeamMatch -> userTeamMatch.getUser().getEmail())
                .collect(Collectors.toList());
        return SimpleTeamResponseDto.builder()
                .id(team.getId())
                .userEmails(userEmails)
                .build();
    }

    @Override
    public Team getTeamById(Long teamId) {
        return teamRepository.findByIdAndNotDeleted(teamId)
                .orElseThrow(() -> new TeamNotFoundException("팀을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    @Override
    public List<SimpleTeamNameResponseDto> getMyTeams(Long userId) {
        List<Team> teams = teamRepository.findTeamsByUserId(userId);
        return teams.stream()
                .map(team -> SimpleTeamNameResponseDto.builder()
                        .id(team.getId())
                        .teamName(team.getTeamName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public TeamMemberResponseDto getMyTeamMembers(Long teamId, Long userId) {
        Team team = teamRepository.findByIdAndNotDeleted(teamId)
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));
        List<User> members = team.getUserTeamMatches().stream()
                .map(UserTeamMatch::getUser)
                .collect(Collectors.toList());

        return TeamMemberResponseDto.builder()
                .currentWeekProgress(team.getWeekProgress().getName())
                .teamName(team.getTeamName())
                .userEmails(members.stream().map(User::getEmail).collect(Collectors.toList()))
                .userNames(members.stream().map(User::getUsername).collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean isSameTeam(Long userId1, Long userId2) {
        List<Team> teamsUser1 = teamRepository.findTeamsByUserId(userId1);
        List<Team> teamsUser2 = teamRepository.findTeamsByUserId(userId2);

        for (Team team1 : teamsUser1) {
            for (Team team2 : teamsUser2) {
                if (team1.getId().equals(team2.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    // 팀 매치에 팀과 유저추가
    private void addUserTeamMatches(Team team, List<User> users) {
        for (User user : users) {
            try {
                UserTeamMatch userTeamMatch = userTeamMatchService.create(user, team);
                userTeamMatchService.save(userTeamMatch);
                team.getUserTeamMatches().add(userTeamMatch);
            } catch (TeamCreationFailedException e) {
                throw new TeamCreationFailedException("UserTeamMatch 생성에 실패했습니다.");
            }
        }
    }

    // 팀 매치에서 유저 제거
    private void removeUserTeamMatches(Team team) {
        List<UserTeamMatch> existingMatches = new ArrayList<>(team.getUserTeamMatches());
        for (UserTeamMatch match : existingMatches) {
            userTeamMatchService.delete(match);
        }
        team.getUserTeamMatches().clear();
        teamRepository.save(team);
    }

    // entity ->dto
    private TeamCreateResponseDto buildTeamCreateResponseDto(Team team, List<User> users) {
        return TeamCreateResponseDto.builder()
                .id(team.getId())
                .leaderId(team.getLeaderId())
                .weekProgress(team.getWeekProgress().getName())
                .userEmails(users.stream().map(User::getEmail).collect(Collectors.toList()))
                .build();
    }
}
