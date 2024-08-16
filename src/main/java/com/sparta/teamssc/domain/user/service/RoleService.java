package com.sparta.teamssc.domain.user.service;

import com.sparta.teamssc.domain.user.entity.Role;

public interface RoleService {

    Role findRoleByName(String name);
}
