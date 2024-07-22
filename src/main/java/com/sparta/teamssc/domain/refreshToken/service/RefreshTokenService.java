package com.sparta.teamssc.domain.refreshToken.service;

import com.sparta.teamssc.domain.refreshToken.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

}
