package com.sparta.teamssc.domain.user.profile.service;

import com.sparta.teamssc.domain.image.service.ImageService;
import com.sparta.teamssc.domain.user.profile.dto.request.PasswordRequestDto;
import com.sparta.teamssc.domain.user.profile.dto.request.ProfileRequestDto;
import com.sparta.teamssc.domain.user.profile.dto.response.ProfileResponseDto;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.repository.userMapping.ProfileCardMapper;
import com.sparta.teamssc.domain.user.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserService userService;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 프로필 생성
     *
     * @param profileRequestDto
     * @param email
     */
    @Override
    @Transactional
    public void updateProfile(ProfileRequestDto profileRequestDto, String email) {

        User existUser = userService.getUserByEmail(email);

        existUser.updateProfile(
                profileRequestDto.getGitLink(),
                profileRequestDto.getVlogLink(),
                profileRequestDto.getIntro(),
                profileRequestDto.getMbti()
        );
    }

    @Override
    public void confirmPassword(PasswordRequestDto passwordRequestDto, String email) {

        User existUser = userService.getUserByEmail(email);

        if (!passwordEncoder.matches(passwordRequestDto.getPassword(), existUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
    }

    @Override
    @Transactional
    public void updatePassword(PasswordRequestDto passwordRequestDto, String email) {

        userService.inValidPassword(passwordRequestDto.getPassword());

        User existUser = userService.getUserByEmail(email);
        String encodedPassword = passwordEncoder.encode(passwordRequestDto.getPassword());

        existUser.updatePassword(encodedPassword);
    }

    @Override
    @Transactional
    public void updateProfileImage(MultipartFile file, String email) {

        User existUser = userService.getUserByEmail(email);

        String profileImage = imageService.updateProfileImage(file);
        existUser.updateProfileImage(profileImage);
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
    public Page<ProfileCardMapper> findMemberCards(int page, String email, String role) {

        Pageable pageable = PageRequest.of(page - 1, 20);

        return userService.findMemberCards(pageable, email, role);
    }
}
