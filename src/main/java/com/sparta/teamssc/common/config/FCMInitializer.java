package com.sparta.teamssc.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class FCMInitializer {

    @Value("${fcm.firebase_config_path}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() {
        try {
            // Firebase 서비스 계정 키 파일로드
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream());
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials)
                    .build();

            // FirebaseApp이 초기화되지 않았다면 초기화
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("firebase 초기화성공");
            }
        } catch (IOException e) {
            log.info("firebase 초기화실패"); // 다른 동작 넣을거임
        }
    }
}
