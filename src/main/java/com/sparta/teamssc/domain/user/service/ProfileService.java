package com.sparta.teamssc.domain.user.service;

import com.sparta.teamssc.domain.user.dto.request.PasswordRequestDto;
import com.sparta.teamssc.domain.user.dto.request.ProfileRequestDto;
import com.sparta.teamssc.domain.user.dto.response.ProfileResponseDto;
import com.sparta.teamssc.domain.user.repository.userMapping.ProfileCardMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

    //프로필 수정
    void updateProfile(ProfileRequestDto profileRequestDto, String email);

    //프로필 사진 수정
    void updateProfileImage(MultipartFile file, String email);
    
    //비밀번호 확인
    void confirmPassword(PasswordRequestDto passwordRequestDto, String email);

    //비밀번호 수정
    void updatePassword(PasswordRequestDto passwordRequestDto, String email);

    //프로필 단건 조회
    ProfileResponseDto searchProfile(Long email);

    //멤버카드들 조회
    Page<ProfileCardMapper> findMemberCards(int page, String email, String role);
}
