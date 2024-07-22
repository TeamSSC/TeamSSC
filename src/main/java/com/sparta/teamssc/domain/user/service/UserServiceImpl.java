package com.sparta.teamssc.domain.user.service;

import com.sparta.teamssc.domain.auth.dto.request.SignupRequest;
import com.sparta.teamssc.domain.auth.util.JwtUtil;
import com.sparta.teamssc.domain.role.entity.Role;
import com.sparta.teamssc.domain.role.userRole.UserRole;
import com.sparta.teamssc.domain.user.entity.User;
import com.sparta.teamssc.domain.user.entity.UserStatus;
import com.sparta.teamssc.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public void signup(SignupRequest signupRequest) {
        String password = signupRequest.getPassword();
        String email = signupRequest.getEmail();

        inValidPassword(password);
        String encodedPassword = passwordEncoder.encode(password);
        inValidEamil(email);

        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(email)
                .password(encodedPassword)
                .status(UserStatus.PENDING)
                .build();
        userRepository.save(user);

    }



    private void inValidPassword(String password) {
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{10,}$")) {
            throw new IllegalArgumentException("비밀번호는 최소 10자 이상이어야 하며, 문자, 숫자, 특수문자를 포함해야 합니다.");
        }
    }
    private void inValidEamil(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }
    }
}
