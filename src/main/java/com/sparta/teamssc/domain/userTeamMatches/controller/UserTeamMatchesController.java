package com.sparta.teamssc.domain.userTeamMatches.controller;

import com.sparta.teamssc.domain.userTeamMatches.service.UserTeamMatchesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserTeamMatchesController {

    private final UserTeamMatchesService userTeamMatchesService;

}
