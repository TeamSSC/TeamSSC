package com.sparta.teamssc.domain.user.user.service;

import com.sparta.teamssc.domain.user.auth.dto.request.LoginRequestDto;
import com.sparta.teamssc.domain.user.auth.dto.request.SignupRequestDto;
import com.sparta.teamssc.domain.user.auth.dto.response.LoginResponse;
import com.sparta.teamssc.domain.user.auth.util.JwtUtil;
import com.sparta.teamssc.domain.user.refreshToken.service.RefreshTokenService;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import com.sparta.teamssc.domain.user.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public void signup(SignupRequestDto signupRequestDto) {

        String password = signupRequestDto.getPassword();
        String email = signupRequestDto.getEmail();

        inValidPassword(password);
        String encodedPassword = passwordEncoder.encode(password);
        inValidEmail(email);

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .email(email)
                .password(encodedPassword)
                .status(UserStatus.PENDING)
                .build();
        userRepository.save(user);

    }

    @Transactional
    @Override
    public LoginResponse login(LoginRequestDto loginRequestDto) {

        User user = getUserByEmail(loginRequestDto.getEmail());

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String accessToken = jwtUtil.createAccessToken(user.getEmail());
        String refreshTokenString = jwtUtil.createRefreshToken(user.getEmail());

        refreshTokenService.updateRefreshToken(user, refreshTokenString);

        user.login();
        userRepository.save(user);

        return new LoginResponse(accessToken, refreshTokenString, user.getUsername());

    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private void inValidPassword(String password) {

        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{10,}$")) {
            throw new IllegalArgumentException("비밀번호는 최소 10자 이상이어야 하며, 문자, 숫자, 특수문자를 포함해야 합니다.");
        }

    }

    private void inValidEmail(String email) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }

    }

    private User findByUsername(String username) {

        if(userRepository.findByUsername(username).isPresent()){
            return userRepository.findByUsername(username).get();
        }
        throw new IllegalArgumentException("해당 유저는 존재하지 않습니다.");

    }

}
