package com.sparta.teamssc.domain.track.service;

import com.sparta.teamssc.domain.track.dto.request.PeriodRequestDto;
import com.sparta.teamssc.domain.track.dto.response.PeriodResponseDto;
import com.sparta.teamssc.domain.track.dto.request.PeriodUpdateRequestDto;
import com.sparta.teamssc.domain.track.entity.Period;

import java.util.List;

public interface PeriodService {

    Period getPeriodById(Long periodId);

    PeriodResponseDto createPeriod(PeriodRequestDto periodRequestDto);

    List<PeriodResponseDto> getAllPeriod();

    void updatePeriod(Long periodId, PeriodUpdateRequestDto periodUpdateRequestDto);

    void deletePeriod(Long periodId);

    List<PeriodResponseDto> getTrackByPeriod(Long trackId);

    boolean isUserInPeriod(Long userId, Long periodId);
}
