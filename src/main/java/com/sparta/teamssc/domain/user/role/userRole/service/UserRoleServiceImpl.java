package com.sparta.teamssc.domain.user.role.userRole.service;

import com.sparta.teamssc.domain.user.role.entity.Role;
import com.sparta.teamssc.domain.user.role.service.RoleService;
import com.sparta.teamssc.domain.user.role.userRole.entity.UserRole;
import com.sparta.teamssc.domain.user.role.userRole.repository.UserRoleRepository;
import com.sparta.teamssc.domain.user.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final RoleService roleService;

    @Override
    public void userRoleAdd(User user,String name) {

        Role role = roleService.findRoleByName(name);

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .build();
        userRoleRepository.save(userRole);
    }
}
