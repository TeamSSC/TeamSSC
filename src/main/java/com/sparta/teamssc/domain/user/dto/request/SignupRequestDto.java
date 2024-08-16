package com.sparta.teamssc.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @Email
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    private String email;         // 이메일

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    private String password;      // 비밀번호

    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String username;      // 이름

    private Long periodId;

    private String adminKey;
}
