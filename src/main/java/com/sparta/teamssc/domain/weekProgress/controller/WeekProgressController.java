package com.sparta.teamssc.domain.weekProgress.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.weekProgress.dto.WeekProgressRequestDto;
import com.sparta.teamssc.domain.weekProgress.dto.WeekProgressResponseDto;
import com.sparta.teamssc.domain.weekProgress.dto.WeekProgressUpdateRequestDto;
import com.sparta.teamssc.domain.weekProgress.dto.WeekProgressbyPeriodResponseDto;
import com.sparta.teamssc.domain.weekProgress.entity.WeekProgress;
import com.sparta.teamssc.domain.weekProgress.service.WeekProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekProgress")
@RequiredArgsConstructor
public class WeekProgressController {

    private final WeekProgressService weekProgressService;

    // 주차 생성
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<ResponseDto<WeekProgressResponseDto>> createWeekProgress(@RequestBody WeekProgressRequestDto requestDto) {

        WeekProgress weekProgress = weekProgressService.createWeekProgress(requestDto.getName(), requestDto.getPeriodId());
        WeekProgressResponseDto responseDto = WeekProgressResponseDto.builder()
                .id(weekProgress.getId())
                .periodId(weekProgress.getPeriod().getId())
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
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<WeekProgressbyPeriodResponseDto>> updateWeekProgress(@PathVariable Long id,
                                                                                   @RequestBody WeekProgressUpdateRequestDto request) {
        WeekProgressbyPeriodResponseDto weekProgress = weekProgressService.updateWeekProgress(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<WeekProgressbyPeriodResponseDto>builder()
                        .message("주차 상태 수정에 성공했습니다.")
                        .data(weekProgress)
                        .build());
    }
    // 상태수정
    @PreAuthorize("hasRole('ROLE_MANAGER')")
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

    // 주차 삭제하기 - 주차의 상태에 따라 삭제가 다름
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deleteWeekProgress(@PathVariable Long id) {
        weekProgressService.deleteWeekProgress(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto<>(null, "주차 상태가 삭제되었습니다."));
    }

    // 기간 ID로 전체 주차 보기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/period/{periodId}")
    public ResponseEntity<ResponseDto<List<WeekProgressbyPeriodResponseDto>>> getWeekProgressByPeriodId(@PathVariable Long periodId) {
        List<WeekProgressbyPeriodResponseDto> weekProgressList = weekProgressService.getWeekProgressByPeriodId(periodId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<List<WeekProgressbyPeriodResponseDto>>builder()
                        .message("기간 ID로 전체 주차 조회에 성공했습니다.")
                        .data(weekProgressList)
                        .build());
    }
}