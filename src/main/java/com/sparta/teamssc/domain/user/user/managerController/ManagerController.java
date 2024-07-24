package com.sparta.teamssc.domain.user.user.managerController;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.user.user.managerService.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
