package com.sparta.teamssc.domain.refreshToken.dto;

import lombok.Getter;

@Getter
public class RefreshTokenRequestDto {

    private String refreshToken;
    private User user;

}
