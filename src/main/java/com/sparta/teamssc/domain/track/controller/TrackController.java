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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseDto<TrackResponseDto>> createTrack(@RequestBody TrackRequestDto trackRequestDto) {

        TrackResponseDto trackResponseDto = trackService.createTrack(trackRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TrackResponseDto>builder()
                        .message("트랙을 생성했습니다.")
                        .data(trackResponseDto)
                        .build());
    }

    /**
     * 트랙 수정 기능 (관리자 권한 필요)
     * @param : 수정할 트랙 id
     * @return : 수정된 트랙 정보 (트랙명)
     */
    @PutMapping("/{trackId}")
    public ResponseEntity<ResponseDto<TrackResponseDto>> updateTrack(@PathVariable Long trackId, @RequestBody TrackRequestDto trackRequestDto) {

        TrackResponseDto trackResponseDto = trackService.updateTrack(trackId, trackRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<TrackResponseDto>builder()
                        .message("트랙이름을 수정했습니다.")
                        .data(trackResponseDto)
                        .build());
    }
}
