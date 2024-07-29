package com.sparta.teamssc.domain.user.profile.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordRequestDto {

    @NotBlank(message = "패스워드값은 존재해야합니다.")
    String password;
}
