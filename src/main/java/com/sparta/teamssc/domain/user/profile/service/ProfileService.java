package com.sparta.teamssc.domain.user.profile.service;

import com.sparta.teamssc.domain.user.profile.dto.ProfileRequestDto;
import com.sparta.teamssc.domain.user.profile.dto.ProfileResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

    void updateProfile(ProfileRequestDto profileRequestDto, String username);

    void updateProfileImage(MultipartFile file, String username);

    ProfileResponseDto searchProfile(String email);
}
