package com.sparta.teamssc.domain.team.weekProgress.service;

import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressResponseDto;
import com.sparta.teamssc.domain.team.weekProgress.dto.WeekProgressUpdateRequestDto;
import com.sparta.teamssc.domain.team.weekProgress.entity.ProgressStatus;
import com.sparta.teamssc.domain.team.weekProgress.entity.WeekProgress;
import com.sparta.teamssc.domain.team.weekProgress.exception.InvalidWeekProgressException;
import com.sparta.teamssc.domain.team.weekProgress.repository.WeekProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeekProgressServiceImpl implements WeekProgressService {
    private final WeekProgressRepository weekProgressRepository;

    // 주차 찾기
    @Override
    public WeekProgress getWeekProgressById(Long id) {

        return weekProgressRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new InvalidWeekProgressException("주차 상태를 찾을 수 없습니다."));
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
        } catch (InvalidWeekProgressException e) {
            throw new InvalidWeekProgressException("주차 상태를 생성할 수 없습니다.");
        }
    }

    // 주차 수정
    @Override
    public WeekProgressResponseDto updateWeekProgress(Long id, WeekProgressUpdateRequestDto requestDto) {
        WeekProgress weekProgress = getWeekProgressById(id);

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
        WeekProgress weekProgress =getWeekProgressById(id);

        weekProgress.updateStatus(status);
        weekProgress = weekProgressRepository.save(weekProgress);
        return WeekProgressResponseDto.builder()
                .id(weekProgress.getId())
                .name(weekProgress.getName())
                .status(weekProgress.getStatus())
                .build();
    }

    /**
     * 주차 삭제: 진행예정은 실수로 만들 확률이 높음 -> 자식 엔티티로 성능저하 가능성 적음
     * 진행중의 삭제는 성능저하 문제 발생 가능성 높음 -> softdelete로 분리
     * @param id
     */
    @Override
    public void deleteWeekProgress(Long id) {
        try {
            WeekProgress weekProgress = getWeekProgressById(id);

            if (weekProgress.getStatus() == ProgressStatus.PLANNED) {
                weekProgressRepository.delete(weekProgress);
            } else {
                weekProgress.delete();
                weekProgressRepository.save(weekProgress);
            }
        } catch (InvalidWeekProgressException e) {
            throw new InvalidWeekProgressException("주차를 삭제할 수 없습니다.");
        }
    }

    // 전체주차 페이징
    @Override
    public Page<WeekProgress> getAllWeekProgress(Pageable pageable) {
        try {
            return weekProgressRepository.findByNotDeleted(pageable);
        } catch (InvalidWeekProgressException e) {
            throw new InvalidWeekProgressException("전체 주차를 페이징 실패했습니다.");
        }
    }
}
