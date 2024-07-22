package com.sparta.teamssc.domain.refreshToken.repository;

import com.sparta.teamssc.domain.refreshToken.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
