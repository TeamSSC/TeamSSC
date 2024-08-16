package com.sparta.teamssc.domain.team.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.team.dto.response.SimpleTeamNameResponseDto;
import com.sparta.teamssc.domain.team.dto.response.TeamMemberResponseDto;
import com.sparta.teamssc.domain.team.service.TeamService;
import com.sparta.teamssc.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/teams")
public class MyTeamController {

    private final TeamService teamService;
    private final UserService userService;

    // 내가 속한 모든 주차의 팀들 조회
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myteam")
    public ResponseEntity<ResponseDto<List<SimpleTeamNameResponseDto>>> getMyTeams(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserByEmail(userDetails.getUsername()).getId();
        List<SimpleTeamNameResponseDto> teams = teamService.getMyTeams(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<List<SimpleTeamNameResponseDto>>builder()
                        .message("내 팀 조회 성공했습니다.")
                        .data(teams)
                        .build());
    }

    // 내가 속한 모든 주차의 팀의 팀원들 조회
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myteam/{teamId}/members")
    public ResponseEntity<ResponseDto<TeamMemberResponseDto>> getMyTeamMembers(@AuthenticationPrincipal UserDetails userDetails,
                                                                               @PathVariable Long teamId) {
        Long userId = userService.getUserByEmail(userDetails.getUsername()).getId();
        TeamMemberResponseDto teamMembers = teamService.getMyTeamMembers(teamId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TeamMemberResponseDto>builder()
                        .message("내 팀의 팀원 조회 성공했습니다.")
                        .data(teamMembers)
                        .build());
    }
}
