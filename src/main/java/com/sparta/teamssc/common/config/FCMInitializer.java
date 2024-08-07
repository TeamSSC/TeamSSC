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

            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                    new ByteArrayInputStream((
                            "{" +
                                    "\"type\": \"service_account\"," +
                                    "\"project_id\": \"" + projectId + "\"," +
                                    "\"private_key_id\": \"" + privateKeyId + "\"," +
                                    "\"private_key\": \"" + formattedPrivateKey + "\"," +
                                    "\"client_email\": \"" + clientEmail + "\"," +
                                    "\"client_id\": \"" + clientId + "\"," +
                                    "\"auth_uri\": \"" + authUri + "\"," +
                                    "\"token_uri\": \"" + tokenUri + "\"," +
                                    "\"auth_provider_x509_cert_url\": \"" + authProviderX509CertUrl + "\"," +
                                    "\"client_x509_cert_url\": \"" + clientX509CertUrl + "\"" +
                                    "}")
                            .getBytes())
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
