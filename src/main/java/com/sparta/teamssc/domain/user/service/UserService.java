package com.sparta.teamssc.domain.user.service;

import com.sparta.teamssc.domain.auth.dto.request.SignupRequest;
import com.sparta.teamssc.domain.auth.entity.LoginRequest;
import com.sparta.teamssc.domain.auth.entity.LoginResponse;
import com.sparta.teamssc.domain.user.entity.User;

public interface UserService {
    // 회원가입
    void signup(SignupRequest signupRequest);

    // 로그인
    LoginResponse login(LoginRequest loginRequest);

    // 이메일로 사용자가져오기
    User getUserByEmail(String email);
}