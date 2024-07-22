package com.sparta.teamssc.domain.refreshToken.controller;

import com.sparta.teamssc.domain.refreshToken.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

}
