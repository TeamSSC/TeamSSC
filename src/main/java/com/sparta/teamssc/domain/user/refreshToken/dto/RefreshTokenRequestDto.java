package com.sparta.teamssc.domain.user.refreshToken.dto;

import com.sparta.teamssc.domain.user.user.entity.User;
import lombok.Getter;

@Getter
public class RefreshTokenRequestDto {
    private String refreshToken;
    private User user;
}
