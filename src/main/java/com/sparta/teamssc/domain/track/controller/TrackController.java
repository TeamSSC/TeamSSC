package com.sparta.teamssc.domain.track.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.track.dto.TrackRequestDto;
import com.sparta.teamssc.domain.track.dto.TrackResponseDto;
import com.sparta.teamssc.domain.track.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
@Slf4j
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

    /**
     * 트랙 조회 기능
     * @param : trackId : 있으면 단건조회, 없으면 페이징 조회
     * @return : 조회된 트랙 리스트 (트랙명)
     */
    @GetMapping
    public ResponseEntity<ResponseDto<?>> getTrack(@RequestParam(required = false) Long trackId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size) {

        log.info(String.valueOf(trackId));

        if (trackId == null) {

            // 페이징 조회
            List<TrackResponseDto> trackResponseDtoList = trackService.getTracks(page, size);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDto.<List<TrackResponseDto>>builder()
                            .message("트랙이름을 조회했습니다.")
                            .data(trackResponseDtoList)
                            .build());
        } else {

            // 단건 조회
            TrackResponseDto trackResponseDto = trackService.getTrack(trackId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDto.<TrackResponseDto>builder()
                            .message("단일트랙을 조회했습니다.")
                            .data(trackResponseDto)
                            .build());
        }
    }
}
