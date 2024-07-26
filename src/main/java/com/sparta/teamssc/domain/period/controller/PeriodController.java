package com.sparta.teamssc.domain.period.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.period.dto.PeriodRequestDto;
import com.sparta.teamssc.domain.period.dto.PeriodResponseDto;
import com.sparta.teamssc.domain.period.service.PeriodServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        periodService.createPeriod(periodRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<PeriodResponseDto>builder()
                .message("기수가 생성 되었습니다.")
                .build());
    }
}
