package com.sparta.teamssc.domain.user.user.service;

import com.sparta.teamssc.domain.user.auth.dto.request.SignupRequest;
import com.sparta.teamssc.domain.user.auth.dto.request.LoginRequest;
import com.sparta.teamssc.domain.user.auth.dto.response.LoginResponse;
import com.sparta.teamssc.domain.user.user.entity.User;

public interface UserService {
    // 회원가입
    void signup(SignupRequest signupRequest);

    // 로그인
    LoginResponse login(LoginRequest loginRequest);

    // 이메일로 사용자가져오기
    User getUserByEmail(String email);
}