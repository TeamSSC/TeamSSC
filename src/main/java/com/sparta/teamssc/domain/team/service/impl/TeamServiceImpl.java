package com.sparta.teamssc.domain.team.service.impl;

import com.sparta.teamssc.domain.team.service.TeamService;
import com.sparta.teamssc.domain.team.service.UserTeamMatchService;
import com.sparta.teamssc.domain.track.entity.Period;
import com.sparta.teamssc.domain.track.service.PeriodService;
import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.request.TeamUpdateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.*;
import com.sparta.teamssc.domain.team.entity.Section;
import com.sparta.teamssc.domain.team.entity.Team;
import com.sparta.teamssc.domain.team.exception.TeamCreationFailedException;
import com.sparta.teamssc.domain.team.exception.TeamNotFoundException;
import com.sparta.teamssc.domain.team.repository.TeamRepository;
import com.sparta.teamssc.domain.track.entity.WeekProgress;
import com.sparta.teamssc.domain.track.service.WeekProgressService;
import com.sparta.teamssc.domain.user.entity.User;
import com.sparta.teamssc.domain.user.service.UserService;
import com.sparta.teamssc.domain.team.entity.UserTeamMatch;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
                    .teamName("Team")
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
    @Transactional(readOnly = true)
    public List<TeamResponseDto> getAllTeamsBySection(Long weekProgressId, Section section) {
        List<Team> teams = teamRepository.findAllByWeekProgressIdAndSectionAndNotDeleted(weekProgressId, section);
        return teams.stream()
                .map(team -> TeamResponseDto.builder()
                        .id(team.getId())
                        .teamName(team.getTeamName())
                        .build())
                .collect(Collectors.toList());
    }


    // 단일 팀불러오기
    @Override
    @Transactional(readOnly = true)
    public SimpleTeamResponseDto getTeamUsers(Long teamId) {
        Team team = getTeamById(teamId);

        List<Long> userIds = team.getUserTeamMatches().stream()
                .map(UserTeamMatch::getUser)
                .map(User::getId)
                .collect(Collectors.toList());

        List<String> userNames = team.getUserTeamMatches().stream()
                .map(UserTeamMatch::getUser)
                .map(User::getUsername)
                .collect(Collectors.toList());

        return SimpleTeamResponseDto.builder()
                .id(team.getId())
                .userIds(userIds)
                .userNames(userNames)
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

    @Override
    public boolean isUserInTeam(Long userId, Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null) {
            throw new IllegalArgumentException("해당 팀이 존재하지 않습니다.");
        }
        return team.getUserTeamMatches().stream()
                .anyMatch(match -> match.getUser().getId().equals(userId));
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
                .userIds(members.stream().map(User::getId).collect(Collectors.toList()))
                .userNames(members.stream().map(User::getUsername).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    @Override
    public TeamUpdateResponseDto updateTeamInfo(Long weekProgressId, Long teamId, TeamUpdateRequestDto teamUpdateRequestDto) {
        try {
            // 현재 사용자 가져오기
            User currentUser = getCurrentUser();

            Team team = getTeamById(teamId);

            // 현재 사용자가 팀원인지 확인
            validateTeamMember(team, currentUser);

            // 리더가 팀원인지 확인
            validateTeamLeader(team, teamUpdateRequestDto.getLeaderId());

            // 팀의 정보 변경
            updateTeamDetails(team, teamUpdateRequestDto);

            return buildTeamUpdateResponseDto(team);

        } catch (TeamNotFoundException e) {
            throw new TeamNotFoundException("팀을 찾을 수 없습니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("팀 정보 수정에 실패했습니다.");
        }
    }

    // 팀 조회하기
    @Transactional(readOnly = true)
    @Override
    public TeamInquiryResponseDto getTeamInfo(Long weekProgressId, Long teamId) {
        Team team = teamRepository.findByIdAndNotDeleted(teamId)
                .orElseThrow(() -> new TeamNotFoundException("팀을 찾을 수 없습니다."));

        User leader = userService.findById(Long.parseLong(team.getLeaderId()));

        return TeamInquiryResponseDto.builder()
                .id(team.getId())
                .teamName(team.getTeamName())
                .leaderId(team.getLeaderId())
                .leaderName(leader.getUsername())
                .teamInfo(team.getTeamDescription())
                .build();
    }

    // 현재 사용자가 맞는지 확인
    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = userDetails.getUsername();
        return userService.getUserByEmail(currentUsername);
    }

    // 팀 멤버인지
    private void validateTeamMember(Team team, User currentUser) {
        boolean isTeamMember = team.getUserTeamMatches().stream()
                .anyMatch(match -> match.getUser().getId().equals(currentUser.getId()));

        if (!isTeamMember) {
            throw new TeamCreationFailedException("팀원이 아닙니다.");
        }
    }

    // 리더가 팀원인지
    private void validateTeamLeader(Team team, Long leaderId) {
        User leader = userService.findById(leaderId);
        boolean isLeaderTeamMember = team.getUserTeamMatches().stream()
                .anyMatch(match -> match.getUser().getId().equals(leader.getId()));

        if (!isLeaderTeamMember) {
            throw new TeamCreationFailedException("리더가 팀원이 아닙니다.");
        }
    }

    //
    private void updateTeamDetails(Team team, TeamUpdateRequestDto teamUpdateRequestDto) {
        team.updateTeamName(teamUpdateRequestDto.getName());
        team.updateLeaderId(teamUpdateRequestDto.getLeaderId());
        team.updateTeamDescription(teamUpdateRequestDto.getTeamInfo());
        teamRepository.save(team);
    }

    private TeamUpdateResponseDto buildTeamUpdateResponseDto(Team team) {
        return TeamUpdateResponseDto.builder()
                .id(team.getId())
                .name(team.getTeamName())
                .leaderId(team.getLeaderId())
                .teamInfo(team.getTeamDescription())
                .build();
    }

}
