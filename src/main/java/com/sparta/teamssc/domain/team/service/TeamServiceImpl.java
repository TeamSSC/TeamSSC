package com.sparta.teamssc.domain.team.service;

import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.period.service.PeriodService;
import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.TeamCreateResponseDto;
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

        } catch (Exception e) {
            throw new TeamCreationFailedException("팀 생성에 실패했습니다.");
        }
    }

    /**
     * 팀 수정하기
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
}