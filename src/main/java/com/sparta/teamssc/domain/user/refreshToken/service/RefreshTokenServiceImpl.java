package com.sparta.teamssc.domain.user.refreshToken.service;

import com.sparta.teamssc.domain.user.refreshToken.entity.RefreshToken;
import com.sparta.teamssc.domain.user.refreshToken.repository.RefreshTokenRepository;
import com.sparta.teamssc.domain.user.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void updateRefreshToken(User user, String token) {

        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUserId(user.getId());

        if (optionalRefreshToken.isPresent()) {
            // 기존 토큰이 존재하는 경우, 해당 토큰을 업데이트합니다.
            RefreshToken existingToken = optionalRefreshToken.get();
            refreshTokenRepository.save(existingToken);
        } else {
            // 기존 토큰이 존재하지 않는 경우, 새로운 토큰을 생성하여 저장합니다.
            RefreshToken newToken = RefreshToken.builder()
                    .user(user)
                    .refreshToken(token)
                    .build();
            refreshTokenRepository.save(newToken);
        }

    }

    @Override
    @Transactional
    public void deleteRefreshToken(User user) {
        refreshTokenRepository.deleteByUserId(user.getId());
    }

}