package com.sparta.teamssc.domain.team.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.TeamCreateResponseDto;
import com.sparta.teamssc.domain.team.dto.response.TeamResponseDto;
import com.sparta.teamssc.domain.team.service.TeamService;
import com.sparta.teamssc.domain.team.weekProgress.service.WeekProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor()
@RequestMapping("api/weekProgress/{weekProgressId}/teams")
public class TeamController {

    private final WeekProgressService weekProgressService;
    private final TeamService teamService;

    // 팀 생성하기
    @PostMapping()
    public ResponseEntity<ResponseDto<TeamCreateResponseDto>> createTeam(@PathVariable Long weekProgressId,
                                                                         @RequestBody TeamCreateRequestDto teamCreateRequestDto) {
        teamCreateRequestDto.updateWeekProgressId(weekProgressId);
        TeamCreateResponseDto teamResponseDto = teamService.createTeam(teamCreateRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TeamCreateResponseDto>builder()
                        .message("팀 단일 생성을 성공했습니다.")
                        .data(teamResponseDto)
                        .build());
    }

    // 팀 수정하기
    @PatchMapping("/{teamId}")
    public ResponseEntity<ResponseDto<TeamCreateResponseDto>> updateTeam(@PathVariable Long weekProgressId,
                                                                         @PathVariable Long teamId,
                                                                         @RequestBody TeamCreateRequestDto teamCreateRequestDto) {
        teamCreateRequestDto.updateWeekProgressId(weekProgressId);
        TeamCreateResponseDto teamResponseDto = teamService.updateTeam(teamId, teamCreateRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TeamCreateResponseDto>builder()
                        .message("팀 수정에 성공했습니다.")
                        .data(teamResponseDto)
                        .build());
    }

    // 팀 삭제하기
    @DeleteMapping("/{teamId}")
    public ResponseEntity<ResponseDto<Void>> deleteTeam(@PathVariable Long weekProgressId, @PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<Void>builder()
                        .message("팀 삭제에 성공했습니다.")
                        .data(null)
                        .build());
    }

    // 주차별 전체 팀 조회하기
    @GetMapping("/lineup")
    public ResponseEntity<ResponseDto<List<TeamCreateResponseDto>>> getAllTeams(@PathVariable Long weekProgressId) {
        
        weekProgressService.getWeekProgressById(weekProgressId);

        List<TeamCreateResponseDto> teams = teamService.getAllTeams(weekProgressId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<List<TeamCreateResponseDto>>builder()
                        .message("팀 편성표 조회 성공했습니다.")
                        .data(teams)
                        .build());
    }
}