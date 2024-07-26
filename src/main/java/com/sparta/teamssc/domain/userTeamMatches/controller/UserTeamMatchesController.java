package com.sparta.teamssc.domain.userTeamMatches.controller;

import com.sparta.teamssc.domain.userTeamMatches.service.UserTeamMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserTeamMatchesController {

    private final UserTeamMatchService userTeamMatchesService;

}
