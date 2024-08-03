package com.sparta.teamssc.domain.fcm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TokenUpdateRequestDto {
    private String newToken; // 새로운 FCM 토큰

    public void updateTokenUpdateRequestDto(String newToken) {
        this.newToken = newToken;
    }
}