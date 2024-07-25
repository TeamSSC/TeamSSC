package com.sparta.teamssc.domain.user.profile.dto;

import com.sparta.teamssc.domain.user.user.entity.UserMbti;
import lombok.Getter;

@Getter
public class ProfileRequestDto {
    private String password;
    private String gitLink;
    private String vlogLink;
    private String intro;
    private UserMbti mbti;
}
