package com.sparta.teamssc.domain.user.role.userRole.repository;

import com.sparta.teamssc.domain.user.role.entity.Role;
import com.sparta.teamssc.domain.user.role.userRole.entity.UserRole;
import com.sparta.teamssc.domain.user.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("DELETE FROM UserRole UR WHERE UR.user = :user AND UR.role = :role")
    void deleteByRole(@Param("user")User user, @Param("role") Role role);
}
