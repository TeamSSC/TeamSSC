package com.sparta.teamssc.domain.user.service;

import com.sparta.teamssc.domain.user.entity.RefreshToken;
import com.sparta.teamssc.domain.user.entity.User;

public interface RefreshTokenService {

    void updateRefreshToken(User user, String token);

    void deleteRefreshToken(User user);

    RefreshToken findRefreshToken(String refreshToken);
}
