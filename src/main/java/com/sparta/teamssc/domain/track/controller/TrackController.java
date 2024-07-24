package com.sparta.teamssc.domain.track.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.track.dto.TrackRequestDto;
import com.sparta.teamssc.domain.track.dto.TrackResponseDto;
import com.sparta.teamssc.domain.track.service.TrackService;
import com.sparta.teamssc.domain.user.auth.service.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    /**
     * 트랙 생성 기능 (관리자 권한 필요)
     * @param : x
     * @return : 생성된 트랙 정보 (트랙명)
     */
    @PostMapping
    public ResponseEntity<ResponseDto<TrackResponseDto>> createTrack(@AuthenticationPrincipal UserDetails userDetails, TrackRequestDto trackRequestDto){

        TrackResponseDto trackResponseDto = trackService.createTrack(userDetails.getUsername(), trackRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TrackResponseDto>builder()
                        .message("트랙을 생성했습니다.")
                        .data(trackResponseDto)
                        .build());

    }


}
