package com.sparta.teamssc.domain.user.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    @Email
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    private String email;
    
    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    private String password;

    @Builder
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
