package com.sparta.teamssc.domain.user.repository;

import com.sparta.teamssc.domain.user.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Modifying
    @Transactional
    @Query("delete from RefreshToken rt where rt.user.id = :userId")
    void deleteByUserId(Long userId);

    Optional<RefreshToken> findByUserId(Long id);

    @Modifying
    @Transactional
    @Query("update RefreshToken rt set rt.refreshToken = :newToken where rt.user.id = :userId")
    void updateByUserId(Long userId, String newToken);
}
