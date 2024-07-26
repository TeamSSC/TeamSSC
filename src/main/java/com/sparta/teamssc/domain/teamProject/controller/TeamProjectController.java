package com.sparta.teamssc.domain.teamProject.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.teamProject.dto.TeamProjectDto;
import com.sparta.teamssc.domain.teamProject.service.TeamProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/weekProgress/{weekProgressId}/teams/{teamId}/page")
public class TeamProjectController {

    private final TeamProjectService teamProjectService;

    /**
     * 팀프로젝트 생성하기
     * @param weekProgressId
     * @param teamId
     * @param teamProjectDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseDto<String>> createTeamProject(@PathVariable Long weekProgressId,
                                                                 @PathVariable Long teamId,
                                                                 @RequestBody TeamProjectDto teamProjectDto) {
        String message = teamProjectService.createTeamProject(teamId, teamProjectDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.<String>builder()
                        .message(message)
                        .build());
    }




}
