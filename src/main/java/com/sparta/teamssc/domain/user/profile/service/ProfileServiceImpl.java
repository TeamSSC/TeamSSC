package com.sparta.teamssc.domain.user.profile.service;

import com.sparta.teamssc.domain.user.profile.dto.ProfileRequestDto;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserService userService;

    /**
     * 프로필 생성
     *
     * @param profileRequestDto
     * @param email
     */
    public void updateProfile(ProfileRequestDto profileRequestDto, String email) {

        User existUser = userService.getUserByEmail(email);
        existUser.updateProfile(
                existUser.getEmail(),
                existUser.getUsername(),
                profileRequestDto.getGitLink(),
                profileRequestDto.getVlogLink(),
                profileRequestDto.getIntro(),
                profileRequestDto.getMbti(),
                profileRequestDto.getProfileImage()
        );
        userService.updateUser(existUser);
    }
}
