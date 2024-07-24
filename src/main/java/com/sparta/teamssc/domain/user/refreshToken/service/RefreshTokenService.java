package com.sparta.teamssc.domain.user.refreshToken.service;

import com.sparta.teamssc.domain.user.refreshToken.entity.RefreshToken;
import com.sparta.teamssc.domain.user.user.entity.User;

public interface RefreshTokenService {

    void updateRefreshToken(User user, String token);

    void deleteRefreshToken(User user);

    RefreshToken findRefreshToken(String refreshToken);
}
