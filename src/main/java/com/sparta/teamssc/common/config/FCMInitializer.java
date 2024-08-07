package com.sparta.teamssc.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
@Slf4j
public class FCMInitializer {

    @Value("${fcm.project-id}")
    private String projectId;

    @Value("${fcm.private-key-id}")
    private String privateKeyId;

    @Value("${fcm.private-key}")
    private String privateKey;

    @Value("${fcm.client-email}")
    private String clientEmail;

    @Value("${fcm.client-id}")
    private String clientId;

    @Value("${fcm.auth-uri}")
    private String authUri;

    @Value("${fcm.token-uri}")
    private String tokenUri;

    @Value("${fcm.auth-provider-x509-cert-url}")
    private String authProviderX509CertUrl;

    @Value("${fcm.client-x509-cert-url}")
    private String clientX509CertUrl;

    @PostConstruct
    public void initialize() {
        log.info("FCMInitializer 초기화");
        try {
            // Replace \n with actual newline characters
            String formattedPrivateKey = privateKey.replace("\\n", "\n");

            String jsonCredentials = String.format(
                    "{"
                            + "\"type\": \"service_account\","
                            + "\"project_id\": \"%s\","
                            + "\"private_key_id\": \"%s\","
                            + "\"private_key\": \"%s\","
                            + "\"client_email\": \"%s\","
                            + "\"client_id\": \"%s\","
                            + "\"auth_uri\": \"%s\","
                            + "\"token_uri\": \"%s\","
                            + "\"auth_provider_x509_cert_url\": \"%s\","
                            + "\"client_x509_cert_url\": \"%s\""
                            + "}",
                    projectId, privateKeyId, formattedPrivateKey, clientEmail, clientId, authUri, tokenUri,
                    authProviderX509CertUrl, clientX509CertUrl
            );

            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                    new ByteArrayInputStream(jsonCredentials.getBytes())
            );

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase 초기화 성공");
            }
        } catch (IOException e) {
            log.error("Firebase 초기화 실패: IOException - {}", e.getMessage(), e); // 예외 메시지 출력
        } catch (Exception e) {
            log.error("Firebase 초기화 실패: 기타 예외 - {}", e.getMessage(), e); // 기타 예외 처리
        }
    }
}
