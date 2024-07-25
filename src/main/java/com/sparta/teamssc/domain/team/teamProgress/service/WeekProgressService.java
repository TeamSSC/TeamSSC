package com.sparta.teamssc.domain.team.teamProgress.service;

import com.sparta.teamssc.domain.team.teamProgress.entity.WeekProgress;
import com.sparta.teamssc.domain.team.teamProgress.repository.WeekProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeekProgressService {
    private final WeekProgressRepository weekProgressRepository;

    public WeekProgress getWeekProgressById(Long id) {
        return weekProgressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주차 상태를 찾을 수 없습니다."));
    }
}