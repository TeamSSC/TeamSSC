package com.sparta.teamssc.domain.user.user.managerService;

import com.sparta.teamssc.domain.user.user.dto.response.PendSignupResponseDto;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import com.sparta.teamssc.domain.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService{

    private final UserRepository userRepository;

    // 회원가입 승인
    @Override
    @Transactional
    public void signupApproval(Long userId) {

        User user = getUserById(userId);

        user.signupApproval();
        userRepository.save(user);

    }

    // 회원가입 거부
    @Override
    @Transactional
    public void signupRefusal(Long userId) {

        User user = getUserById(userId);

        user.signupRefusal();
        userRepository.save(user);

    }

    // 회원가입 승인 대기자 조회
    @Override
    public List<PendSignupResponseDto> getPendSignup() {

        List<User> pendSignupList = userRepository.findByStatus(UserStatus.PENDING);

        return pendSignupList.stream()
                .map(user -> new PendSignupResponseDto(user.getId(), user.getEmail(), user.getUsername(), user.getStatus()))
                .collect(Collectors.toList());
    }

    // userId 조회
    @Override
    public User getUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("회원가입 승인 대기 사용자를 찾을 수 없습니다."));
    }
}
