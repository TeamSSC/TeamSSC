package com.sparta.teamssc.domain.user.role.userRole.repository;

import com.sparta.teamssc.domain.user.role.entity.Role;
import com.sparta.teamssc.domain.user.role.userRole.entity.UserRole;
import com.sparta.teamssc.domain.user.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

//    @Modifying
//    @Query("DELETE FROM UserRole ur WHERE ur.user.id = :user_id AND ur.role.id = :role_id")
//    void deleteByRole(@Param("user_id") Long userid, @Param("role_id") Long roleId);

}
