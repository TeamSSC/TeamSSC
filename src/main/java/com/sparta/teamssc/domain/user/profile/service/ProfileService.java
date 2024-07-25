package com.sparta.teamssc.domain.user.profile.service;

import com.sparta.teamssc.domain.user.profile.dto.ProfileRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

    void updateProfile(ProfileRequestDto profileRequestDto, String username);

    void updateProfileImage(MultipartFile file, String username);
}
