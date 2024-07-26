package com.sparta.teamssc.domain.period.service;

import com.sparta.teamssc.domain.period.dto.PeriodRequestDto;
import com.sparta.teamssc.domain.period.dto.PeriodResponseDto;
import com.sparta.teamssc.domain.period.entity.Period;

public interface PeriodService {

    Period getPeriodById(Long periodId);
    PeriodResponseDto createPeriod(PeriodRequestDto periodRequestDto);
}
