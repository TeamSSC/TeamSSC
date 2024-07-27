package com.sparta.teamssc.domain.user.user.managerService;

import com.sparta.teamssc.domain.user.user.dto.response.PendSignupResponseDto;

import java.util.List;

public interface ManagerService {

    void signupApproval(Long userId);

    void signupRefusal(Long userId);

    List<PendSignupResponseDto> getPendSignup();
}
