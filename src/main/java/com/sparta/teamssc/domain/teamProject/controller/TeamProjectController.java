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
    public ResponseEntity<ResponseDto<Void>> createTeamProject(@PathVariable Long weekProgressId,
                                                               @PathVariable Long teamId,
                                                               @RequestBody TeamProjectDto teamProjectDto) {
        teamProjectService.createTeamProject(teamId, teamProjectDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.<Void>builder()
                        .message("팀페이지 작성에 성공했습니다.")
                        .build());
    }


    /**
     * 팀프로젝트 수정
     * @param weekProgressId
     * @param teamId
     * @param teamProjectDto
     * @return
     */
    @PatchMapping
    public ResponseEntity<ResponseDto<Void>> updateTeamProject(@PathVariable Long weekProgressId,
                                                               @PathVariable Long teamId,
                                                               @RequestBody TeamProjectDto teamProjectDto) {
        teamProjectService.updateTeamProject(teamId, teamProjectDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<Void>builder()
                        .message("팀페이지 수정에 성공했습니다.")
                        .build());
    }

}
