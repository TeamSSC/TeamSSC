package com.sparta.teamssc.domain.track.service;

import com.sparta.teamssc.domain.track.dto.response.WeekProgressResponseDto;
import com.sparta.teamssc.domain.track.dto.request.WeekProgressUpdateRequestDto;
import com.sparta.teamssc.domain.track.dto.response.WeekProgressbyPeriodResponseDto;
import com.sparta.teamssc.domain.track.entity.ProgressStatus;
import com.sparta.teamssc.domain.track.entity.WeekProgress;

import java.util.List;


public interface WeekProgressService {
    WeekProgress getWeekProgressById(Long id);
    WeekProgress createWeekProgress(String name, Long periodId);
    WeekProgressbyPeriodResponseDto updateWeekProgress(Long id, WeekProgressUpdateRequestDto requestDto);

    WeekProgressResponseDto updateWeekProgressStatus(Long id, ProgressStatus status);

    void deleteWeekProgress(Long id);

    List<WeekProgressbyPeriodResponseDto> getWeekProgressByPeriodId(Long periodId);
}