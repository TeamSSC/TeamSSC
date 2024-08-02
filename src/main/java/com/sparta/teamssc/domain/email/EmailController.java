package com.sparta.teamssc.domain.email;

import com.sparta.teamssc.domain.email.EmailService;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

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

//    /**
//     * 이메일 인증
//     * @param username 사용자 이름
//     * @param verificationCode 인증 코드
//     * @return 인증 성공 또는 실패 메시지
//     */
//    @PostMapping("/verify-email")
//    public ResponseEntity<String> verifyEmail(@RequestParam String username, @RequestParam String verificationCode) {
//        boolean isVerified = userService.verifyEmail(username, verificationCode);
//        if (isVerified) {
//            return ResponseEntity.ok("이메일 인증 성공");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 인증 실패");
//        }
//    }
}

