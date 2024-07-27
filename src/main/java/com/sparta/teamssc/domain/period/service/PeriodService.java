package com.sparta.teamssc.domain.period.service;

import com.sparta.teamssc.domain.period.dto.PeriodRequestDto;
import com.sparta.teamssc.domain.period.dto.PeriodResponseDto;
import com.sparta.teamssc.domain.period.dto.PeriodUpdateRequestDto;
import com.sparta.teamssc.domain.period.entity.Period;

import java.util.List;

public interface PeriodService {

    Period getPeriodById(Long periodId);
    PeriodResponseDto createPeriod(PeriodRequestDto periodRequestDto);
    List<PeriodResponseDto> getAllPeriod();
    void updatePeriod(Long periodId, PeriodUpdateRequestDto periodUpdateRequestDto);
}
