package com.sparta.teamssc.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String email;         // 이메일
    private String password;      // 비밀번호
    private String username;      // 이름
}
