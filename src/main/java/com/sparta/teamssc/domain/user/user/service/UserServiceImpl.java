package com.sparta.teamssc.domain.user.user.service;

import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.period.service.PeriodService;
import com.sparta.teamssc.domain.user.auth.dto.request.LoginRequestDto;
import com.sparta.teamssc.domain.user.auth.dto.request.SignupRequestDto;
import com.sparta.teamssc.domain.user.auth.dto.response.KakaoUserStatusResponse;
import com.sparta.teamssc.domain.user.auth.dto.response.LoginResponseDto;
import com.sparta.teamssc.domain.user.auth.util.JwtUtil;
import com.sparta.teamssc.domain.user.refreshToken.entity.RefreshToken;
import com.sparta.teamssc.domain.user.refreshToken.service.RefreshTokenService;
import com.sparta.teamssc.domain.user.role.userRole.entity.UserRole;
import com.sparta.teamssc.domain.user.role.userRole.service.UserRoleService;
import com.sparta.teamssc.domain.user.user.dto.response.PendSignupResponseDto;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import com.sparta.teamssc.domain.user.user.repository.UserRepository;
import com.sparta.teamssc.domain.user.user.repository.userMapping.ProfileCardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final UserRoleService userRoleService;
    private final PeriodService periodService;
//    private final RedisTemplate<String, String> redisTemplate;

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

        if (signupRequestDto.getAdminKey() != null && signupRequestDto.getPeriodId() == null) {
            if (signupRequestDto.getAdminKey().equals(adminKey)) {

                User user = User.builder()
                        .username(signupRequestDto.getUsername())
                        .email(email)
                        .password(encodedPassword)
                        .status(UserStatus.ACTIVE)
                        .build();
                userRepository.save(user);
                userRoleService.userRoleAdd(user, "admin");
            } else {
                throw new IllegalArgumentException("유효하지 않은 어드민토큰입니다.");
            }
        } else {

            Period period = periodService.getPeriodById(signupRequestDto.getPeriodId());
            User user = User.builder()
                    .username(signupRequestDto.getUsername())
                    .email(email)
                    .password(encodedPassword)
                    .status(UserStatus.PENDING)
                    .period(period)
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

        List<String> roles = user.getRoles().stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toList());


        String accessToken = JwtUtil.createAccessToken(user.getEmail(), roles);
        String refreshToken = JwtUtil.createRefreshToken(user.getEmail(), roles);

        refreshTokenService.updateRefreshToken(user, refreshToken);

        // FCM 토큰 업데이트
        user.updateFcmToken(loginRequestDto.getFcmToken());
        userRepository.save(user);


        user.login();

        if (user.getPeriod() == null) {
            return LoginResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .username(user.getUsername())
                    .userStatus(user.getStatus())
                    .build();
        }

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .periodId(user.getPeriod().getId())
                .period(user.getPeriod().getPeriod())
                .trackName(user.getPeriod().getTrack().getName())
                .userStatus(user.getStatus())
                .fcmToken(user.getFcmToken())
                .build();
    }

    @Override
    public void logout(String email) {

        User user = getUserByEmail(email);

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

        List<String> roles = user.getRoles().stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toList());

        String newAccessToken = JwtUtil.createAccessToken(user.getEmail(), roles); // 새로운 엑세스 토큰 생성
        String newRefreshToken = JwtUtil.createRefreshToken(user.getEmail(), roles); // 새로운 리프레시 토큰 생성


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

//    /**
//     * 이메일 인증: 입력한 인증 코드를 검증
//     *
//     * @param email         사용자 이름
//     * @param verificationCode 입력한 인증 코드
//     * @return 인증 성공 여부
//     */
//    @Override
//    @Transactional
//    public boolean verifyEmail(String email, String verificationCode) {
//        String storedCode = redisTemplate.opsForValue().get(email);
//        if (storedCode != null && storedCode.equals(verificationCode)) {
//            Optional<User> userOptional = userRepository.findByEmail(email);
//            if (userOptional.isPresent()) {
//                User user = userOptional.get();
//                user.updateStatus();
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public Page<ProfileCardMapper> findMemberCards(Pageable pageable, String email, String role) {

        User user = getUserByEmail(email);
        Period period = user.getPeriod();

        return userRepository.findMemberCards(period, role, pageable);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void inValidPassword(String password) {

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
    public Page<PendSignupResponseDto> findPagedPendList(Pageable pageable, Period period) {
        return userRepository.findPagedPendList(pageable, period);
    }



    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<UserRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                .collect(Collectors.toList());
    }

    // 기수에 대한 유저가져오기
    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersByPeriodId(Long periodId) {

        Period period = periodService.getPeriodById(periodId);

        return userRepository.findAllByPeriodId(periodId);
    }

    // fcm토큰 수정
    @Transactional
    public void updateFcmToken(Long userId, String fcmToken) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        user.updateFcmToken(fcmToken);

        userRepository.save(user);
    }

    // 카카오 가입 유저 기수 신청 상태 확인
    @Override
    public KakaoUserStatusResponse getKakaoUserStatus(String email) {

        User user = getUserByEmail(email);

        if (user.getPeriod() == null) {
            return KakaoUserStatusResponse.builder().build();
        }

        return KakaoUserStatusResponse.builder()
                .period(user.getPeriod().getPeriod())
                .periodId(user.getPeriod().getId())
                .trackName(user.getPeriod().getTrack().getName())
                .build();
    }

    // 카카오 유저 기수 신청
    @Transactional
    @Override
    public void kakaoUserUpdatePeriod(Long periodId, String email) {

        Period period = periodService.getPeriodById(periodId);
        User user = getUserByEmail(email);
        if (user.getPeriod() != null) {
            throw new IllegalArgumentException("이미 트랙을 신청한 상태입니다.");
        }

        user.updateKakaoUserPeriod(period);
    }
}
