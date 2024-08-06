package com.sparta.teamssc.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class FCMInitializer {

    @Value("${FIREBASE_JSON}")
    private String firebaseJson;

    @PostConstruct
    public void initialize() {
        log.info("FCMInitializer 초기화");
        try {
            log.info("Loading Firebase service account key from path: {}", firebaseJson);

            // Firebase 서비스 계정 키 파일로드
            ByteArrayInputStream inputStream = new ByteArrayInputStream(firebaseJson.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(inputStream);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials)
                    .build();

            // FirebaseApp이 초기화되지 않았다면 초기화
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("firebase 초기화성공");
            }
        } catch (IOException e) {
            log.error("firebase 초기화실패: IOException - {}", e.getMessage(), e); // 예외 메시지 출력
        } catch (Exception e) {
            log.error("firebase 초기화실패: 기타 예외 - {}", e.getMessage(), e); // 기타 예외 처리
        }
    }
}
