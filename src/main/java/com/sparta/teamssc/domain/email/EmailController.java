package com.sparta.teamssc.domain.email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    /**
     * 이메일 발송 메서드
     * @param email
     * @return 바디에 반환
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendVerificationEmail(@RequestParam("email") String email) {
        try {
            String authNumber = emailService.joinEmail(email);
            return ResponseEntity.ok(authNumber);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("이메일 전송에 실패했습니다.");
        }
    }
}

