package com.sparta.teamssc.domain.user.user.repository;

import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
    Optional<User> findById(Long userId);

    List<User> findByStatus(UserStatus userStatus);
}
