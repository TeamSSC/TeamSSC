package com.sparta.teamssc.domain.team.controller;

import com.sparta.teamssc.domain.team.service.UserTeamMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserTeamMatchesController {

    private final UserTeamMatchService userTeamMatchesService;

}
