package com.sparta.teamssc.domain.user.user.repository;

import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import com.sparta.teamssc.domain.user.user.repository.userMapping.ProfileCardMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"roles", "roles.role"})
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    List<User> findByStatus(UserStatus userStatus);

    Page<ProfileCardMapper> findAllByOrderByCreateAtDesc(Pageable pageable);
}
