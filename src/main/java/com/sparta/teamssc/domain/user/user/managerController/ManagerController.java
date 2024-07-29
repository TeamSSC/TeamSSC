package com.sparta.teamssc.domain.user.user.managerController;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.user.user.dto.request.ApproveManagerRequestDto;
import com.sparta.teamssc.domain.user.user.dto.response.PendSignupResponseDto;
import com.sparta.teamssc.domain.user.user.managerService.ManagerService;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ManagerController {

    private final ManagerService managerService;

    /**
     * 회원가입 승인 메서드
     * @param userId
     * @return 바디에 반환
     */
    @PatchMapping("/approve/{userId}")
    public ResponseEntity<ResponseDto<String>> signupApproval(@PathVariable Long userId) {

        managerService.signupApproval(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<String>builder()
                .message("회원가입이 승인 되었습니다.")
                .build());
    }

    /**
     * 회원가입 승인 거부 메서드
     * @param userId
     * @return 바디에 반환
     */
    @PatchMapping("/refusal/{userId}")
    public ResponseEntity<ResponseDto<String>> signupRefusal(@PathVariable Long userId) {

        managerService.signupRefusal(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<String>builder()
                .message("회원가입 승인이 거부 되었습니다.")
                .build());
    }

    /**
     * 회원가입 승인 대기자 조회 메서드
     * @return 바디에 반환
     */
    @GetMapping("/signup/pend")
    public ResponseEntity<ResponseDto<Page<PendSignupResponseDto>>> getPendSignup(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                                  @AuthenticationPrincipal UserDetails userDetails) {

        Page<PendSignupResponseDto> responseDto = managerService.getPendSignup(page - 1, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<Page<PendSignupResponseDto>>builder()
                        .message("회원가입 승인 대기자가 조회 되었습니다.")
                        .data(responseDto)
                        .build());
    }

    @PostMapping("/period/manager")
    public ResponseEntity<ResponseDto<String>> approveManager (@RequestBody ApproveManagerRequestDto requestDto){

        managerService.approveManager(requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("매니저 권한 등록 완료")
                        .build());
    }
}
