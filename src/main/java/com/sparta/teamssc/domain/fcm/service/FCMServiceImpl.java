package com.sparta.teamssc.domain.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sparta.teamssc.domain.fcm.dto.TokenNotificationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

@Service
public class FCMServiceImpl implements FCMService {
    // FCM 토큰을 으로 메시지 전송
    public void sendByToken(TokenNotificationRequestDto requestDto) {
        // FCM 메시지 객체를 생성
        Message message = Message.builder()
                .putData("title", requestDto.getTitle())
                .putData("message", requestDto.getMessage())
                .setToken(requestDto.getTargetToken())
                .build();


        try {
            // FirebaseMessaging 인스턴스로 메시지 전송
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("FCM 메시지 전송 실패", e);
        }
    }
}
