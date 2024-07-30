package com.sparta.teamssc.domain.user.user.managerService;

import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.user.role.userRole.service.UserRoleService;
import com.sparta.teamssc.domain.user.user.dto.request.ApproveManagerRequestDto;
import com.sparta.teamssc.domain.user.user.dto.response.PendSignupResponseDto;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import com.sparta.teamssc.domain.user.user.repository.UserRepository;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService{

    private final UserService userService;
    private final UserRoleService userRoleService;

    // 회원가입 승인
    @Override
    @Transactional
    public void signupApproval(Long userId) {

        User user = userService.findById(userId);

        user.signupApproval();
        userService.updateUser(user);

    }

    // 회원가입 거부
    @Override
    @Transactional
    public void signupRefusal(Long userId) {

        User user = userService.findById(userId);

        user.signupRefusal();
        userService.updateUser(user);

    }

    // 회원가입 승인 대기자 조회
    @Override
    public Page<PendSignupResponseDto> getPendSignup(int page, String email) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createAt"));

        Period period = userService.getUserByEmail(email).getPeriod();

        Page<PendSignupResponseDto> pendPage = userService.findPagedPendList(pageable, period);

        return pendPage;
    }

    //매니저 권한 부여
    @Override
    public void approveManager(ApproveManagerRequestDto approveManagerRequestDto) {

        User user = userService.getUserByEmail(approveManagerRequestDto.getUserEmail());

        userRoleService.userRoleAdd(user,"manager");
    }
}
