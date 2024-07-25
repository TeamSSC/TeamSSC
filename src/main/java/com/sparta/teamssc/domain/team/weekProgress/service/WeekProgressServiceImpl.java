package com.sparta.teamssc.domain.team.weekProgress.service;

import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressResponseDto;
import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressUpdateRequestDto;
import com.sparta.teamssc.domain.team.weekProgress.entity.ProgressStatus;
import com.sparta.teamssc.domain.team.weekProgress.entity.WeekProgress;
import com.sparta.teamssc.domain.team.weekProgress.repository.WeekProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeekProgressServiceImpl implements WeekProgressService {
    private final WeekProgressRepository weekProgressRepository;

    // 주차 찾기
    @Override
    public WeekProgress getWeekProgressById(Long id) {

        return weekProgressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주차 상태를 찾을 수 없습니다."));
    }

    // 주차 생성
    @Override
    public WeekProgress createWeekProgress(String name) {

        try {
            WeekProgress weekProgress = WeekProgress.builder()
                    .name(name)
                    .status(ProgressStatus.PLANNED)
                    .build();
            return weekProgressRepository.save(weekProgress);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("주차 상태를 생성할 수 없습니다.");
        }
    }

    // 주차 수정
    @Override
    public WeekProgressResponseDto updateWeekProgress(Long id, WeekProgressUpdateRequestDto requestDto) {
        WeekProgress weekProgress = weekProgressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주차 상태를 찾을 수 없습니다."));
        weekProgress.updateStatus(requestDto.getStatus());
        weekProgress = weekProgressRepository.save(weekProgress);
        return WeekProgressResponseDto.builder()
                .id(weekProgress.getId())
                .name(weekProgress.getName())
                .status(weekProgress.getStatus())
                .build();
    }


    // 주차 상태 수정 : 상태 조정은 별개로도 이뤄져야함
    @Override
    public WeekProgressResponseDto updateWeekProgressStatus(Long id, ProgressStatus status) {
        WeekProgress weekProgress = weekProgressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주차 상태를 찾을 수 없습니다."));
        weekProgress.updateStatus(status);
        weekProgress = weekProgressRepository.save(weekProgress);
        return WeekProgressResponseDto.builder()
                .id(weekProgress.getId())
                .name(weekProgress.getName())
                .status(weekProgress.getStatus())
                .build();
    }

    // 주차 삭제
    @Override
    public void deleteWeekProgress(Long id) {

    }

    // 전체주차
    @Override
    public List<WeekProgress> getAllWeekProgress() {
        return null;
    }
}
