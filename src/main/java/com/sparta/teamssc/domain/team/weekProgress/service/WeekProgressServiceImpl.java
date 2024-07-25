package com.sparta.teamssc.domain.team.weekProgress.service;

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
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("주차 상태를 생성할 수 없습니다.");
        }
    }

    // 주차 수정
    @Override
    public WeekProgress updateWeekProgress(Long id, String name) {
        return null;
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
