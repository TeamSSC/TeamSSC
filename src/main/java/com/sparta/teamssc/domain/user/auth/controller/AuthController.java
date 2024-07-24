package com.sparta.teamssc.domain.user.auth.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.user.auth.dto.request.SignupRequestDto;
import com.sparta.teamssc.domain.user.auth.dto.request.LoginRequestDto;
import com.sparta.teamssc.domain.user.auth.dto.response.LoginResponseDto;
import com.sparta.teamssc.domain.user.auth.util.JwtUtil;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/users/signup")
    public ResponseEntity<ResponseDto<String>> signup(@RequestBody SignupRequestDto signupRequestDto) {

        userService.signup(signupRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.<String>builder()
                        .message("회원가입 성공했습니다..")
                        .build());
    }

    // 로그인
    /**
     * 로그인
     *
     * @param loginRequestDto
     * @return 헤더에 반환
     */
    @PostMapping("/users/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<LoginResponseDto>builder()
                        .message("로그인 성공했습니다.")
                        .data(userService.login(loginRequestDto))
                        .build());
    }

    @PostMapping("/users/logout")
    public ResponseEntity<ResponseDto<String>> logout(@AuthenticationPrincipal UserDetails userDetails) {

        userService.logout(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("로그아웃 성공했습니다.")
                        .build());
    }

    @PostMapping("/users/token/refresh")
    public ResponseEntity<ResponseDto<LoginResponseDto>> tokenRefresh(@RequestHeader("refreshToken") String refreshToken) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<LoginResponseDto>builder()
                        .message("재 로그인 성공했습니다.")
                        .data(userService.tokenRefresh(refreshToken))
                        .build());
    }

}