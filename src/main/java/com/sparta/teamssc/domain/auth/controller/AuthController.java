package com.sparta.teamssc.domain.auth.controller;

import com.sparta.teamssc.domain.auth.dto.request.SignupRequest;
import com.sparta.teamssc.domain.auth.entity.LoginRequest;
import com.sparta.teamssc.domain.auth.entity.LoginResponse;
import com.sparta.teamssc.domain.auth.util.JwtUtil;
import com.sparta.teamssc.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    // 로그인
    /**
     * 로그인
     *
     * @param loginRequest
     * @return 헤더에 반환
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse tokens = userService.login(loginRequest); // 로그인 시도 및 토큰 생성
        String accessToken = tokens.getAccessToken();
        String refreshToken = tokens.getRefreshToken();

        // 각 토큰을 별도의 헤더에 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        headers.set("Refresh-Token", refreshToken);

        return new ResponseEntity<>("로그인 성공", headers, HttpStatus.OK);
    }

}