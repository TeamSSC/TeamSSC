package com.sparta.teamssc.domain.user.user.managerService;

import com.sparta.teamssc.domain.user.user.dto.request.ApproveManagerRequestDto;
import com.sparta.teamssc.domain.user.user.dto.response.PendSignupResponseDto;
import com.sparta.teamssc.domain.user.user.entity.User;

import java.util.List;

public interface ManagerService {

    void signupApproval(Long userId);

    User getUserById(Long userId);

    void signupRefusal(Long userId);

    List<PendSignupResponseDto> getPendSignup();

    void approveManager(ApproveManagerRequestDto approveManagerRequestDto);
}
