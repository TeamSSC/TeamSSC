package com.sparta.teamssc.domain.user.role.userRole.repository;

import com.sparta.teamssc.domain.user.role.entity.Role;
import com.sparta.teamssc.domain.user.role.userRole.entity.UserRole;
import com.sparta.teamssc.domain.user.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Modifying
    @Query("DELETE FROM UserRole ur WHERE ur.user.id = :userId AND ur.role.id = :roleId")
    void deleteByRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

}

