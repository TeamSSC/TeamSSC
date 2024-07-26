package com.sparta.teamssc.domain.user.role.service;

import com.sparta.teamssc.domain.user.role.entity.Role;

public interface RoleService {

    Role findRoleByName(String name);
}
