package com.sparta.teamssc.domain.user.dto.request;

import com.sparta.teamssc.domain.user.entity.User;
import lombok.Getter;

@Getter
public class RefreshTokenRequestDto {
    private String refreshToken;
    private User user;
}