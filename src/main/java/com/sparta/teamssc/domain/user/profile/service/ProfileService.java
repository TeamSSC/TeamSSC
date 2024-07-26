package com.sparta.teamssc.domain.user.profile.service;

import com.sparta.teamssc.domain.user.profile.dto.ProfileRequestDto;
import com.sparta.teamssc.domain.user.profile.dto.ProfileResponseDto;
import com.sparta.teamssc.domain.user.user.repository.userMapping.ProfileCardMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

    //프로필 수정
    void updateProfile(ProfileRequestDto profileRequestDto, String username);

    //프로필 사진 수정
    void updateProfileImage(MultipartFile file, String username);

    //프로필 단건 조회
    ProfileResponseDto searchProfile(Long email);

    //멤버카드들 조회
    Page<ProfileCardMapper> getAllProfiles(int page);
}
