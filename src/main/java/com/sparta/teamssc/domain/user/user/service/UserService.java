package com.sparta.teamssc.domain.user.user.service;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.period.dto.PeriodResponseDto;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.user.auth.dto.request.SignupRequestDto;
import com.sparta.teamssc.domain.user.auth.dto.request.LoginRequestDto;
import com.sparta.teamssc.domain.user.auth.dto.response.KakaoUserStatusResponse;
import com.sparta.teamssc.domain.user.auth.dto.response.LoginResponseDto;
import com.sparta.teamssc.domain.user.user.dto.response.PendSignupResponseDto;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import com.sparta.teamssc.domain.user.user.repository.userMapping.ProfileCardMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    // 회원가입
    void signup(SignupRequestDto signupRequestDto);

    // 로그인
    LoginResponseDto login(LoginRequestDto loginRequestDto);

    // 이메일로 사용자가져오기
    User getUserByEmail(String email);

    //username으로 사용자 가져오기
    User findByUsername(String email);

    //로그아웃
    void logout(String email);

    //리프레시토큰으로 토큰 재발급 후에 로그인
    LoginResponseDto tokenRefresh(String refreshToken);

    //프로필 수정
    void updateUser(User user);

    //회원탈퇴
    void withdrawn(String email);

    //비밀번호 확인
    void inValidPassword(String password);

    //유저id값으로 사용자 가져오기
    User findById(Long id);

    //유저 페이징
    Page<ProfileCardMapper> findMemberCards(Pageable pageable, String email, String role);

    // 회원가입 대기 상태 유저 페이징
    Page<PendSignupResponseDto> findPagedPendList(Pageable pageable, Period period);


    // 카카오 가입 유저 기수 신청 상태 확인
    KakaoUserStatusResponse getKakaoUserStatus(String email);

    void kakaoUserUpdatePeriod(Long periodId, String email);

//    boolean verifyEmail(String username, String verificationCode);
}