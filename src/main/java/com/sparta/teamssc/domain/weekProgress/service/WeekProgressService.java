package com.sparta.teamssc.domain.weekProgress.service;

import com.sparta.teamssc.domain.weekProgress.dto.WeekProgressResponseDto;
import com.sparta.teamssc.domain.weekProgress.dto.WeekProgressUpdateRequestDto;
import com.sparta.teamssc.domain.weekProgress.entity.ProgressStatus;
import com.sparta.teamssc.domain.weekProgress.entity.WeekProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface WeekProgressService {
    WeekProgress getWeekProgressById(Long id);

    WeekProgress createWeekProgress(String name);

    WeekProgressResponseDto updateWeekProgress(Long id, WeekProgressUpdateRequestDto requestDto);

    WeekProgressResponseDto updateWeekProgressStatus(Long id, ProgressStatus status);

    void deleteWeekProgress(Long id);

    Page<WeekProgress> getAllWeekProgress(Pageable pageable);

}