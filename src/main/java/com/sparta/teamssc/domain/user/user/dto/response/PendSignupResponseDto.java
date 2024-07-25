package com.sparta.teamssc.domain.user.user.dto.response;

import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PendSignupResponseDto {

    private final Long userId;
    private final String email;
    private final String username;
    private final UserStatus status;

    @Builder
    public PendSignupResponseDto(Long userId, String email, String username, UserStatus status) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.status = status;
    }
}
