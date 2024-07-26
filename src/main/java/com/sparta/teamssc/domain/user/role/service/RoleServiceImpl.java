package com.sparta.teamssc.domain.user.role.service;

import com.sparta.teamssc.domain.user.role.entity.Role;
import com.sparta.teamssc.domain.user.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
