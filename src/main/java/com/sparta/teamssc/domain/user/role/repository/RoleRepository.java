package com.sparta.teamssc.domain.user.role.repository;

import com.sparta.teamssc.domain.user.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
