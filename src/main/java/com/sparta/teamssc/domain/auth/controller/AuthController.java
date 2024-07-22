package com.sparta.teamssc.domain.auth.controller;

import com.sparta.teamssc.domain.auth.dto.request.SignupRequest;
import com.sparta.teamssc.domain.auth.util.JwtUtil;
import com.sparta.teamssc.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

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

}