package com.sparta.teamssc.domain.team.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.request.TeamUpdateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.*;
import com.sparta.teamssc.domain.team.entity.Section;
import com.sparta.teamssc.domain.team.service.TeamService;
import com.sparta.teamssc.domain.track.service.WeekProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor()
@RequestMapping("api/weekProgress/{weekProgressId}/teams")
public class TeamController {

    private final WeekProgressService weekProgressService;
    private final TeamService teamService;

    // 팀 생성하기
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping()
    public ResponseEntity<ResponseDto<TeamCreateResponseDto>> createTeam(@PathVariable Long weekProgressId,
                                                                         @RequestBody TeamCreateRequestDto teamCreateRequestDto) {

        TeamCreateResponseDto teamResponseDto = teamService.createTeam(weekProgressId, teamCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.<TeamCreateResponseDto>builder()
                        .message("팀 단일 생성을 성공했습니다.")
                        .data(teamResponseDto)
                        .build());
    }

    // 팀 수정하기
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PatchMapping("/{teamId}")
    public ResponseEntity<ResponseDto<TeamCreateResponseDto>> updateTeam(@PathVariable Long weekProgressId,
                                                                         @PathVariable Long teamId,
                                                                         @RequestBody TeamCreateRequestDto teamCreateRequestDto) {

        TeamCreateResponseDto teamResponseDto = teamService.updateTeam(weekProgressId, teamId, teamCreateRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TeamCreateResponseDto>builder()
                        .message("팀 수정에 성공했습니다.")
                        .data(teamResponseDto)
                        .build());
    }

    // 팀 삭제하기
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @DeleteMapping("/{teamId}")
    public ResponseEntity<ResponseDto<Void>> deleteTeam(@PathVariable Long weekProgressId, @PathVariable Long teamId) {
        teamService.deleteTeam(weekProgressId, teamId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<Void>builder()
                        .message("팀 삭제에 성공했습니다.")
                        .data(null)
                        .build());
    }

    // 단일 팀 불러오기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{teamId}/users")
    public ResponseEntity<ResponseDto<SimpleTeamResponseDto>> getTeamUsers(@PathVariable Long weekProgressId,
                                                                           @PathVariable Long teamId) {
        SimpleTeamResponseDto teamUsers = teamService.getTeamUsers(teamId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<SimpleTeamResponseDto>builder()
                        .message("팀 유저 조회 성공했습니다.")
                        .data(teamUsers)
                        .build());
    }

    // 팀 전체 라인업 보기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/lineup")
    public ResponseEntity<ResponseDto<List<TeamResponseDto>>> getAllTeams(@PathVariable Long weekProgressId, @RequestParam Section section) {
        weekProgressService.getWeekProgressById(weekProgressId);

        List<TeamResponseDto> teams = teamService.getAllTeamsBySection(weekProgressId, section);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<List<TeamResponseDto>>builder()
                        .message("팀 편성표 조회 성공했습니다.")
                        .data(teams)
                        .build());
    }

    // 팀 정보 수정하기 - Leader, 팀명
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{teamId}/teamInfo")
    public ResponseEntity<ResponseDto<TeamUpdateResponseDto>> updateTeamInfo(@PathVariable Long weekProgressId,
                                                                             @PathVariable Long teamId,
                                                                             @RequestBody TeamUpdateRequestDto teamUpdateRequestDto) {
        TeamUpdateResponseDto teamResponseDto = teamService.updateTeamInfo(weekProgressId, teamId, teamUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TeamUpdateResponseDto>builder()
                        .message("팀 정보 수정에 성공했습니다.")
                        .data(teamResponseDto)
                        .build());
    }

    // 팀 정보 조회하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{teamId}")
    public ResponseEntity<ResponseDto<TeamInquiryResponseDto>> getTeamInfo(@PathVariable Long weekProgressId,
                                                                    @PathVariable Long teamId) {
        TeamInquiryResponseDto teamResponseDto = teamService.getTeamInfo(weekProgressId, teamId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TeamInquiryResponseDto>builder()
                        .message("팀 정보 조회에 성공했습니다.")
                        .data(teamResponseDto)
                        .build());
    }
}