package com.sparta.teamssc.domain.team.weekProgress.service;

import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressResponseDto;
import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressUpdateRequestDto;
import com.sparta.teamssc.domain.team.weekProgress.entity.ProgressStatus;
import com.sparta.teamssc.domain.team.weekProgress.entity.WeekProgress;

import java.util.List;


public interface WeekProgressService {
    WeekProgress getWeekProgressById(Long id);

    WeekProgress createWeekProgress(String name);

    WeekProgressResponseDto updateWeekProgress(Long id, WeekProgressUpdateRequestDto requestDto);

    WeekProgressResponseDto updateWeekProgressStatus(Long id, ProgressStatus status);

    void deleteWeekProgress(Long id);

    List<WeekProgress> getAllWeekProgress();
}