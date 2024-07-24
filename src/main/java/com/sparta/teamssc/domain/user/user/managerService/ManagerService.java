package com.sparta.teamssc.domain.user.user.managerService;

import com.sparta.teamssc.domain.user.user.entity.User;

public interface ManagerService {

    void signupApproval(Long userId);

    User getUserById(Long userId);

    void signupRefusal(Long userId);
}
