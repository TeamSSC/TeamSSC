package com.sparta.teamssc.domain.user.service.impl;

import com.sparta.teamssc.domain.user.entity.Role;
import com.sparta.teamssc.domain.user.repository.RoleRepository;
import com.sparta.teamssc.domain.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
