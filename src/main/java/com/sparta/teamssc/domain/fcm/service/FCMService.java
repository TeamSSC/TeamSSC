package com.sparta.teamssc.domain.fcm.service;

import com.sparta.teamssc.domain.fcm.dto.TokenNotificationRequestDto;
import org.springframework.http.ResponseEntity;

public interface FCMService {
    void sendByToken(TokenNotificationRequestDto requestDto);
}
