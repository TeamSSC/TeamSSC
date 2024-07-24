package com.sparta.teamssc.domain.team.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.TeamCreateResponseDto;
import com.sparta.teamssc.domain.team.dto.response.TeamResponseDto;
import com.sparta.teamssc.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor()
@RequestMapping("api/teams")
public class TeamController {

    private final TeamService teamService;

    // 팀 생성하기
    @PostMapping()
    public ResponseEntity<ResponseDto<TeamCreateResponseDto>> createTeam(@RequestBody TeamCreateRequestDto teamCreateRequestDto) {

        TeamCreateResponseDto teamResponseDto = teamService.createTeam(teamCreateRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TeamCreateResponseDto>builder()
                        .message("팀단일생성을 성공했습니다.")
                        .data(teamResponseDto)
                        .build());

    }

    // 팀수정하기
    @PatchMapping("/{teamId}")
    public ResponseEntity<ResponseDto<TeamCreateResponseDto>> updateTeam(@PathVariable Long teamId,
                                                                         @RequestBody TeamCreateRequestDto teamCreateRequestDto) {
        TeamCreateResponseDto teamResponseDto = teamService.updateTeam(teamId, teamCreateRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TeamCreateResponseDto>builder()
                        .message("팀 수정에 성공했습니다.")
                        .data(teamResponseDto)
                        .build());
    }

    // 팀 삭제하기
    @DeleteMapping("/{teamId}")
    public ResponseEntity<ResponseDto<Void>> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<Void>builder()
                        .message("팀 삭제에 성공했습니다.")
                        .data(null)
                        .build());
    }
}