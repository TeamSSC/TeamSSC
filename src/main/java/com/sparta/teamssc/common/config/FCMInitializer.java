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

    @Value("${fcm.firebase_project_id}")
    private String projectId;

    @Value("${fcm.firebase_private_key_id}")
    private String privateKeyId;

    @Value("${fcm.firebase_private_key}")
    private String privateKey;

    @Value("${fcm.firebase_client_email}")
    private String clientEmail;

    @Value("${fcm.firebase_client_id}")
    private String clientId;

    @Value("${fcm.firebase_auth_uri}")
    private String authUri;

    @Value("${fcm.firebase_token_uri}")
    private String tokenUri;

    @Value("${fcm.firebase_auth_provider_cert_url}")
    private String authProviderCertUrl;

    @Value("${fcm.firebase_client_cert_url}")
    private String clientCertUrl;

    @Value("${fcm.firebase_universe_domain}")
    private String universeDomain;

    @PostConstruct
    public void initialize() {
        log.info("FCMInitializer 초기화");
        try {
            log.info("Loading Firebase service account key from environment variables");

            // 줄 바꿈 문자를 적절히 처리
            String privateKey = System.getenv("FIREBASE_PRIVATE_KEY").replace("\\n", "\n");

            // 환경 변수를 JSON 형식으로 변환
            String json = String.format(
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
                            + "\"client_x509_cert_url\": \"%s\","
                            + "\"universe_domain\": \"%s\""
                            + "}",
                    projectId, privateKeyId, privateKey, clientEmail, clientId, authUri, tokenUri,
                    authProviderCertUrl, clientCertUrl, universeDomain
            );

            // JSON 문자열을 InputStream으로 변환
            ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

            // Firebase 서비스 계정 키 파일로드
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