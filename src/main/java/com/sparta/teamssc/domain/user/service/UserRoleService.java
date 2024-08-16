package com.sparta.teamssc.domain.user.service;

import com.sparta.teamssc.domain.user.entity.User;

public interface UserRoleService {
    void userRoleAdd(User user, String role);

    void userRoleDelete(Long userId, Long roleId);
}
