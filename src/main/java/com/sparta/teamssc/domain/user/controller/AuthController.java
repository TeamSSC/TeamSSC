package com.sparta.teamssc.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.user.dto.request.SignupRequestDto;
import com.sparta.teamssc.domain.user.dto.request.LoginRequestDto;
import com.sparta.teamssc.domain.user.dto.response.KakaoUserStatusResponse;
import com.sparta.teamssc.domain.user.dto.response.LoginResponseDto;
import com.sparta.teamssc.domain.user.service.KakaoService;
import com.sparta.teamssc.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final KakaoService kakaoService;

    // 회원가입
    @PostMapping("/users/signup")
    public ResponseEntity<ResponseDto<String>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {

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
     * @return 엑세스토큰, 리프레시토큰, 유저네임반환
     */
    @PostMapping("/users/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<LoginResponseDto>builder()
                        .message("로그인 성공했습니다.")
                        .data(userService.login(loginRequestDto))
                        .build());
    }

    /**
     * 로그아웃
     *
     * @param userDetails
     * @return 로그아웃 성공 메세지
     */
    @PostMapping("/users/logout")
    public ResponseEntity<ResponseDto<String>> logout(@AuthenticationPrincipal UserDetails userDetails) {

        userService.logout(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("로그아웃 성공했습니다.")
                        .build());
    }

    /**
     * 리프레시 토큰으로 토큰 재발급 및 재로그인
     *
     * @param refreshToken
     * @return 재로그인 메세지와 데이터
     */
    @PostMapping("/users/token/refresh")
    public ResponseEntity<ResponseDto<LoginResponseDto>> tokenRefresh(@RequestHeader("refreshToken") String refreshToken) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<LoginResponseDto>builder()
                        .message("재 로그인 성공했습니다.")
                        .data(userService.tokenRefresh(refreshToken))
                        .build());
    }

    /**
     * 회원탈퇴
     *
     * @param userDetails
     * @return 탈퇴성공했다는 메세지
     */
    @PostMapping("/users/withdrawn")
    public ResponseEntity<ResponseDto<String>> withdrawn(@AuthenticationPrincipal UserDetails userDetails) {

        userService.withdrawn(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("회원탈퇴 성공했습니다.")
                        .build());
    }

    /**
     * 카카오 로그인
     */
    @GetMapping("/user/kakao/callback")
    public ResponseEntity<ResponseDto<LoginResponseDto>> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {

        LoginResponseDto loginResponseDto = kakaoService.kakaoLogin(code);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<LoginResponseDto>builder()
                        .message("카카오 로그인")
                        .data(loginResponseDto)
                        .build());
    }

    // 카카오 가입 유저 기수 신청 상태 확인
    @GetMapping("/kakao/users/status")
    public ResponseEntity<ResponseDto<KakaoUserStatusResponse>> getKakaoUserStatus(@AuthenticationPrincipal UserDetails userDetails) {

        KakaoUserStatusResponse kakaoUserStatusResponse = userService.getKakaoUserStatus(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<KakaoUserStatusResponse>builder()
                        .message("카카오 유저 기수 신청 상태를 조회했습니다.")
                        .data(kakaoUserStatusResponse)
                        .build());
    }

    // 카카오 유저 기수 신청
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/kakao/users/periods/{periodId}")
    public ResponseEntity<ResponseDto<String>> kakaoUserUpdatePeriod(@PathVariable Long periodId,
                                                                     @AuthenticationPrincipal UserDetails userDetails) {

        userService.kakaoUserUpdatePeriod(periodId, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("기수 신청에 성공했습니다.")
                        .build());
    }
}