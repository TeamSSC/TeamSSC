package com.sparta.teamssc.domain.team.service;

import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.request.TeamUpdateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.*;
import com.sparta.teamssc.domain.team.entity.Section;
import com.sparta.teamssc.domain.team.entity.Team;

import java.util.List;

public interface TeamService {
    TeamCreateResponseDto createTeam(Long weekProgressId, TeamCreateRequestDto teamCreateRequestDto);

    TeamCreateResponseDto updateTeam(Long weekProgressId, Long teamId, TeamCreateRequestDto teamCreateRequestDto);

    void deleteTeam(Long weekProgressId, Long teamId);

    //전체 라인업 sesstion A, B에 따라
    List<TeamResponseDto> getAllTeamsBySection (Long weekProgressId, Section section);
    
    Team getTeamById(Long teamId); //전체 팀 라인업
    List<SimpleTeamNameResponseDto> getMyTeams(Long userId); // 내가 속한 팀
    TeamMemberResponseDto getMyTeamMembers(Long teamId, Long userId); //내가 속한 팀의 팀원들

    //단일팀
    SimpleTeamResponseDto getTeamUsers(Long teamId);

    TeamUpdateResponseDto updateTeamInfo(Long weekProgressId,
                                         Long teamId,
                                         TeamUpdateRequestDto teamUpdateRequestDto);

    boolean isUserInTeam(Long userId, Long teamId);
}
