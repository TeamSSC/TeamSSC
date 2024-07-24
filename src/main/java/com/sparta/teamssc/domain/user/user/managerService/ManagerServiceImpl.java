package com.sparta.teamssc.domain.user.user.managerService;

import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService{

    private final UserRepository userRepository;

    /**
     * 회원가입 승인 메서드
     * @param userId
     */
    @Override
    @Transactional
    public void signupApproval(Long userId) {

        User user = getUserById(userId);

        user.signupApproval();
        userRepository.save(user);

    }

    @Override
    public User getUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("회원가입 승인 대기 사용자를 찾을 수 없습니다."));

    }

}
