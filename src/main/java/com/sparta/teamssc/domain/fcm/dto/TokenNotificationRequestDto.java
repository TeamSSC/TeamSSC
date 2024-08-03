package com.sparta.teamssc.domain.fcm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 알림보낼때 setter을 쓰는 경우가 일반적이라 어노테이션에 넣었음
public class TokenNotificationRequestDto {
    private String targetToken;  // FCM 토큰
    private String title;        // 알림 제목
    private String message;      // 알림 메시지
}
