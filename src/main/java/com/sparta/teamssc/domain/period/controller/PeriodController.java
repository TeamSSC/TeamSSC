package com.sparta.teamssc.domain.period.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.period.dto.PeriodRequestDto;
import com.sparta.teamssc.domain.period.dto.PeriodResponseDto;
import com.sparta.teamssc.domain.period.dto.PeriodUpdateRequestDto;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.period.entity.PeriodStatus;
import com.sparta.teamssc.domain.period.service.PeriodServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PeriodController {

    private final PeriodServiceImpl periodService;

    /**
     * 기수 생성 메서드
     * @param periodRequestDto
     * @return 바디에 반환
     */
    @PostMapping("/periods")
    public ResponseEntity<ResponseDto<PeriodResponseDto>> createPeriod(@RequestBody PeriodRequestDto periodRequestDto) {

        PeriodResponseDto periodResponseDto = periodService.createPeriod(periodRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<PeriodResponseDto>builder()
                .message("기수가 생성 되었습니다.")
                .data(periodResponseDto)
                .build());
    }

    /**
     * 기수 전체 조회 메서드
     * @return 바디에 반환
     */
    @GetMapping("/periods")
    public ResponseEntity<ResponseDto<List<PeriodResponseDto>>> getAllPeriod() {
        List<PeriodResponseDto> periodResponseDtoList = periodService.getAllPeriod();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<List<PeriodResponseDto>>builder()
                .message("기수가 조회 되었습니다.")
                .data(periodResponseDtoList)
                .build());
    }

    /**
     * 기수 상태 수정 메서드
     * @param periodId
     * @param periodUpdateRequestDto
     * @return 바디에 반환
     */
    @PatchMapping("/periods/{periodId}")
    public ResponseEntity<ResponseDto<Void>> updatePeriod(@PathVariable Long periodId,
                                                          @RequestBody PeriodUpdateRequestDto periodUpdateRequestDto) {
        periodService.updatePeriod(periodId, periodUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<Void>builder()
                .message("기수가 상태가 변경 되었습니다.")
                .build());
    }

    /**
     * 기수 삭제 메서드
     * @param periodId
     * @return 바디에 반환
     */
    @DeleteMapping("/periods/{periodId}")
    public ResponseEntity<ResponseDto<Void>> deletePeriod(@PathVariable Long periodId) {

        periodService.deletePeriod(periodId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<Void>builder()
                .message("기수가 삭제 되었습니다.")
                .build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/tracks/{trackId}/periods")
    public ResponseEntity<ResponseDto<List<PeriodResponseDto>>> getAllTrackPeriods(@PathVariable Long trackId) {

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<List<PeriodResponseDto>>builder()
                .message("트랙에 대한 기수 조회 성공")
                .data(periodService.getTrackByPeriod(trackId))
                .build());
    }

}
