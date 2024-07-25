package com.sparta.teamssc.domain.user.profile.service;

import com.sparta.teamssc.domain.user.profile.dto.ProfileRequestDto;

public interface ProfileService {

    void updateProfile(ProfileRequestDto profileRequestDto, String username);
}
