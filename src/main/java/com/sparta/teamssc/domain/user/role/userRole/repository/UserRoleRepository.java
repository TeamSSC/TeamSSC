package com.sparta.teamssc.domain.user.role.userRole.repository;

import com.sparta.teamssc.domain.user.role.userRole.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
