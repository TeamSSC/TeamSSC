package com.sparta.teamssc.domain.user.user.service;

import com.sparta.teamssc.domain.user.auth.dto.request.LoginRequestDto;
import com.sparta.teamssc.domain.user.auth.dto.request.SignupRequestDto;
import com.sparta.teamssc.domain.user.auth.dto.response.LoginResponseDto;
import com.sparta.teamssc.domain.user.auth.util.JwtUtil;
import com.sparta.teamssc.domain.user.refreshToken.entity.RefreshToken;
import com.sparta.teamssc.domain.user.refreshToken.service.RefreshTokenService;
import com.sparta.teamssc.domain.user.role.userRole.service.UserRoleService;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import com.sparta.teamssc.domain.user.user.repository.UserRepository;
import com.sparta.teamssc.domain.user.user.repository.userMapping.ProfileCardMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final UserRoleService userRoleService;

    @Value("${secret.admin-key}")
    String adminKey;


    @Transactional
    @Override
    public void signup(SignupRequestDto signupRequestDto) {

        String password = signupRequestDto.getPassword();
        String email = signupRequestDto.getEmail();

        inValidPassword(password);
        String encodedPassword = passwordEncoder.encode(password);
        inValidEmail(email);

        if (!Objects.isNull(signupRequestDto.getAdminKey())) {
            if(signupRequestDto.getAdminKey().equals(adminKey)) {
                User user = User.builder()
                        .username(signupRequestDto.getUsername())
                        .email(email)
                        .password(encodedPassword)
                        .status(UserStatus.ACTIVE)
                        .build();
                userRepository.save(user);
                userRoleService.userRoleAdd(user, "admin");
            } else{
                throw new IllegalArgumentException("유효하지 않은 어드민토큰입니다.");
            }
        } else {

            User user = User.builder()
                    .username(signupRequestDto.getUsername())
                    .email(email)
                    .password(encodedPassword)
                    .status(UserStatus.PENDING)
                    .build();
            userRepository.save(user);
            userRoleService.userRoleAdd(user, "user");
        }
    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        User user = getUserByEmail(loginRequestDto.getEmail());

        if (user.getStatus() == UserStatus.PENDING) {
            throw new IllegalArgumentException("아직 승인 받지 않은 회원입니다.");
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String accessToken = JwtUtil.createAccessToken(user.getEmail());
        String refreshToken = JwtUtil.createRefreshToken(user.getEmail());

        refreshTokenService.updateRefreshToken(user, refreshToken);

        user.login();
        userRepository.save(user);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .build();
    }

    @Override
    public void logout(String email) {

        User user = getUserByEmail(email);

        user.logout();

        refreshTokenService.deleteRefreshToken(user);
    }

    @Override
    public LoginResponseDto tokenRefresh(String refreshToken) {

        RefreshToken currentToken = refreshTokenService.findRefreshToken(refreshToken); // 리프레시 토큰 검색

        if (currentToken == null || !JwtUtil.validateRefreshToken(currentToken.getRefreshToken())) {
            throw new IllegalArgumentException("다시 로그인 해주세요."); // 리프레시 토큰이 유효하지 않으면 예외 발생
        }
        String email = JwtUtil.getUsernameFromToken(refreshToken);
        User user = getUserByEmail(email);

        String newAccessToken = JwtUtil.createAccessToken(user.getEmail()); // 새로운 엑세스 토큰 생성
        String newRefreshToken = JwtUtil.createRefreshToken(user.getEmail()); // 새로운 리프레시 토큰 생성


        refreshTokenService.updateRefreshToken(user, newRefreshToken); // 리프레시 토큰 업데이트

        return LoginResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .username(user.getUsername())
                .build();
    }

    @Override
    public void withdrawn(String email) {

        User user = getUserByEmail(email);

        user.withdrawn();

        userRepository.save(user);
    }

    @Override
    public Page<ProfileCardMapper> findAllUsers(Pageable pageable) {
        return userRepository.findAllByOrderByCreateAtDesc(pageable);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
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

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));
    }

    @Override
    public User findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));
    }

    @Override
    public List<User> findByStatus(UserStatus status) {
        return userRepository.findByStatus(UserStatus.PENDING);
    }

}
