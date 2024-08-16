package com.sparta.teamssc.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.teamssc.domain.user.dto.KakaoUserInfoDto;
import com.sparta.teamssc.domain.user.dto.response.LoginResponseDto;
import com.sparta.teamssc.domain.user.entity.User;

public interface KakaoService {

    LoginResponseDto kakaoLogin(String code) throws JsonProcessingException;

    String getToken(String code) throws JsonProcessingException;

    KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException;

    User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo);
}
