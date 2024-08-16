package com.sparta.teamssc.domain.user.dto.response;

import com.sparta.teamssc.domain.user.entity.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PendSignupResponseDto {

    private Long userId;
    private String email;
    private String username;
    private UserStatus status;
    private LocalDateTime createAt;

    @Builder
    public PendSignupResponseDto(Long userId, String email, String username, UserStatus status, LocalDateTime createAt) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.status = status;
        this.createAt = createAt;
    }
}
