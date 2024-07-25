package com.sparta.teamssc.domain.user.profile.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.user.profile.dto.ProfileRequestDto;
import com.sparta.teamssc.domain.user.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final ProfileService profileService;

    @PatchMapping("/user/profile/update")
    public ResponseEntity<ResponseDto<String>> updateProfile(@RequestBody ProfileRequestDto profileRequestDto,
                                                             @AuthenticationPrincipal UserDetails userDetails) {

        profileService.updateProfile(profileRequestDto,userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("프로필 수정 성공했습니다.")
                        .build());
    }
}
