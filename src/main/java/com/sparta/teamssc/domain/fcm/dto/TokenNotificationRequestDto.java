package com.sparta.teamssc.domain.fcm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TokenNotificationRequestDto {
    private String targetToken;  // FCM 토큰
    private String title;        // 알림 제목
    private String message;      // 알림 메시지
}
