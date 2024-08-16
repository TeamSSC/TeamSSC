package com.sparta.teamssc.domain.user.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.user.dto.request.PasswordRequestDto;
import com.sparta.teamssc.domain.user.dto.request.ProfileRequestDto;
import com.sparta.teamssc.domain.user.dto.response.ProfileResponseDto;
import com.sparta.teamssc.domain.user.service.ProfileService;
import com.sparta.teamssc.domain.user.repository.userMapping.ProfileCardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final ProfileService profileService;

    @PatchMapping("/users/profile/update")
    public ResponseEntity<ResponseDto<String>> updateProfile(@RequestBody ProfileRequestDto profileRequestDto,
                                                             @AuthenticationPrincipal UserDetails userDetails) {

        profileService.updateProfile(profileRequestDto, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("프로필 수정 성공했습니다.")
                        .build());
    }

    @PatchMapping("/users/profile/image/update")
    public ResponseEntity<ResponseDto<String>> updateProfileImage(@RequestPart MultipartFile file,
                                                                  @AuthenticationPrincipal UserDetails userDetails) {

        profileService.updateProfileImage(file, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("프로필 이미지 수정 성공했습니다.")
                        .build());
    }

    @PostMapping("/users/profile/password/confirm")
    public ResponseEntity<ResponseDto<String>> confirmPassword(@RequestBody PasswordRequestDto passwordRequestDto,
                                                               @AuthenticationPrincipal UserDetails userDetails) {

        profileService.confirmPassword(passwordRequestDto, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("비밀번호 확인 성공했습니다.")
                        .build());
    }

    @PatchMapping("/users/profile/password/update")
    public ResponseEntity<ResponseDto<String>> updateProfilePassword(@RequestBody PasswordRequestDto passwordRequestDto,
                                                                     @AuthenticationPrincipal UserDetails userDetails) {

        profileService.updatePassword(passwordRequestDto, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("비밀번호 수정 성공했습니다.")
                        .build());
    }

    @GetMapping("/users/{userId}/profile")
    public ResponseEntity<ResponseDto<ProfileResponseDto>> getProfile(@PathVariable Long userId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<ProfileResponseDto>builder()
                        .message("프로필 조회 성공했습니다.")
                        .data(profileService.searchProfile(userId))
                        .build());
    }

    @GetMapping("/users/profiles")
    public ResponseEntity<ResponseDto<Page<ProfileCardMapper>>> getAllProfiles(@RequestParam int page,
                                                                               @RequestParam String role,
                                                                               @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<Page<ProfileCardMapper>>builder()
                        .message("프로필카드 목록 조회에 성공했습니다.")
                        .data(profileService.findMemberCards(page,userDetails.getUsername(),role))
                        .build());
    }
}
