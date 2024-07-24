package com.sparta.teamssc.domain.team.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.team.dto.request.TeamCreateRequestDto;
import com.sparta.teamssc.domain.team.dto.response.TeamCreateResponseDto;
import com.sparta.teamssc.domain.team.dto.response.TeamResponseDto;
import com.sparta.teamssc.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor()
@RequestMapping("api/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping()
    public ResponseEntity<ResponseDto<TeamCreateResponseDto>> createTeam(@RequestBody TeamCreateRequestDto teamCreateRequestDto) {

        TeamCreateResponseDto teamResponseDto = teamService.createTeam(teamCreateRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TeamCreateResponseDto>builder()
                        .message("팀단일생성을 성공했습니다.")
                        .data(teamResponseDto)
                        .build());

    }
}