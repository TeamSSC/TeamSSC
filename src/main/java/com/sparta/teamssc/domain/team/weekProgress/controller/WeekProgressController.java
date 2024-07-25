package com.sparta.teamssc.domain.team.weekProgress.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressRequestDto;
import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressResponseDto;
import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressUpdateRequestDto;
import com.sparta.teamssc.domain.team.weekProgress.entity.ProgressStatus;
import com.sparta.teamssc.domain.team.weekProgress.entity.WeekProgress;
import com.sparta.teamssc.domain.team.weekProgress.service.WeekProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weekProgress")
@RequiredArgsConstructor
public class WeekProgressController {

    private final WeekProgressService weekProgressService;

    // 주차 생성
    @PostMapping
    public ResponseEntity<ResponseDto<WeekProgressResponseDto>> createWeekProgress(@RequestBody WeekProgressRequestDto requestDto) {

        WeekProgress weekProgress = weekProgressService.createWeekProgress(requestDto.getName());
        WeekProgressResponseDto responseDto = WeekProgressResponseDto.builder()
                .id(weekProgress.getId())
                .name(weekProgress.getName())
                .status(weekProgress.getStatus())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.<WeekProgressResponseDto>builder()
                        .message("주차 상태 생성에 성공했습니다.")
                        .data(responseDto)
                        .build());
    }

    // 주차 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<WeekProgressResponseDto>> updateWeekProgress(@PathVariable Long id,
                                                                                   @RequestBody WeekProgressUpdateRequestDto request) {
        WeekProgressResponseDto weekProgress = weekProgressService.updateWeekProgress(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<WeekProgressResponseDto>builder()
                        .message("주차 상태 수정에 성공했습니다.")
                        .data(weekProgress)
                        .build());
    }
    // 상태수정
    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseDto<WeekProgressResponseDto>> updateWeekProgressStatus(@PathVariable Long id,
                                                                                         @RequestBody WeekProgressUpdateRequestDto request) {
        WeekProgressResponseDto weekProgress = weekProgressService.updateWeekProgressStatus(id, request.getStatus());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<WeekProgressResponseDto>builder()
                        .message("주차 상태 수정에 성공했습니다.")
                        .data(weekProgress)
                        .build());
    }
}