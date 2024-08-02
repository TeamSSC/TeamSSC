package com.sparta.teamssc.domain.user.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String nickname;
    private String imageUrl;
    private String email;

    public KakaoUserInfoDto(Long id, String nickname, String imageUrl, String email) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.email = email;
    }
}