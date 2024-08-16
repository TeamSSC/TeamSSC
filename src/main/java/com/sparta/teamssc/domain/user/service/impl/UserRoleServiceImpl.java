package com.sparta.teamssc.domain.user.service.impl;

import com.sparta.teamssc.domain.user.entity.Role;
import com.sparta.teamssc.domain.user.entity.UserRole;
import com.sparta.teamssc.domain.user.repository.UserRoleRepository;
import com.sparta.teamssc.domain.user.entity.User;
import com.sparta.teamssc.domain.user.service.RoleService;
import com.sparta.teamssc.domain.user.service.UserRoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final RoleService roleService;

    @Override
    public void userRoleAdd(User user,String role) {

        Role findRole = roleService.findRoleByName(role);

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(findRole)
                .build();
        userRoleRepository.save(userRole);
    }

    @Override
    @Transactional
    public void userRoleDelete(Long userId, Long roleId) {
        userRoleRepository.deleteByRole(userId, roleId);
    }
}
