package com.sparta.teamssc.domain.user.profile.service;

import com.sparta.teamssc.domain.image.service.ImageService;
import com.sparta.teamssc.domain.user.profile.dto.ProfileRequestDto;
import com.sparta.teamssc.domain.user.profile.dto.ProfileResponseDto;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.repository.userMapping.ProfileCardMapper;
import com.sparta.teamssc.domain.user.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserService userService;
    private final ImageService imageService;

    /**
     * 프로필 생성
     *
     * @param profileRequestDto
     * @param email
     */
    @Override
    public void updateProfile(ProfileRequestDto profileRequestDto, String email) {

        User existUser = userService.getUserByEmail(email);

        existUser.updateProfile(
                existUser.getUsername(),
                profileRequestDto.getPassword(),
                profileRequestDto.getGitLink(),
                profileRequestDto.getVlogLink(),
                profileRequestDto.getIntro(),
                profileRequestDto.getMbti()
        );
        userService.updateUser(existUser);
    }

    @Override
    @Transactional
    public void updateProfileImage(MultipartFile file, String email) {

        User existUser = userService.getUserByEmail(email);

        String profileImage = imageService.updateProfileImage(file);
        existUser.updateProfileImage(profileImage);

        userService.updateUser(existUser);
    }

    @Override
    public ProfileResponseDto searchProfile(Long userId) {

        User user = userService.findById(userId);

        return ProfileResponseDto.builder()
                .username(user.getUsername())
                .gitLink(user.getGitLink())
                .vlogLink(user.getVlogLink())
                .intro(user.getIntro())
                .mbti(user.getMbti())
                .profileImage(user.getProfileImage())
                .section(user.getSection())
                .email(user.getEmail())
                .build();
    }

    @Override
    public Page<ProfileCardMapper> getAllProfiles(int page) {
            Pageable pageable = PageRequest.of(page - 1, 10);

            Page<ProfileCardMapper> profileCards = userService.findAllUsers(pageable);

            return profileCards;
    }
}
