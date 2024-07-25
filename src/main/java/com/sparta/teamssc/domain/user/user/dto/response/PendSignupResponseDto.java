package com.sparta.teamssc.domain.user.user.dto.response;

import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import lombok.Getter;

@Getter
public class PendSignupResponseDto {

    private Long userId;
    private String email;
    private String username;
    private UserStatus status;

    public PendSignupResponseDto(Long userId, String email, String username, UserStatus status) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.status = status;
    }
}
