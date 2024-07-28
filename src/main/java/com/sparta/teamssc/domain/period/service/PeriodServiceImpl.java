package com.sparta.teamssc.domain.period.service;

import com.sparta.teamssc.domain.period.dto.PeriodRequestDto;
import com.sparta.teamssc.domain.period.dto.PeriodResponseDto;
import com.sparta.teamssc.domain.period.dto.PeriodUpdateRequestDto;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.period.entity.PeriodStatus;
import com.sparta.teamssc.domain.period.repository.PeriodRepository;
import com.sparta.teamssc.domain.track.entity.Track;
import com.sparta.teamssc.domain.track.service.TrackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PeriodServiceImpl implements PeriodService {

    private final PeriodRepository periodRepository;
    private final TrackServiceImpl trackService;

    // 기수 생성
    @Override
    public PeriodResponseDto createPeriod(PeriodRequestDto periodRequestDto) {

        Track track = trackService.searchTrack(periodRequestDto.getTrackId());

                Period period = Period.builder()
                .track(track)
                .period(periodRequestDto.getPeriod())
                .status(periodRequestDto.getStatus())
                .build();

        periodRepository.save(period);

        return PeriodResponseDto.builder()
                .id(track.getId())
                .period(periodRequestDto.getPeriod())
                .status(periodRequestDto.getStatus())
                .build();
    }

    // 기수 전체 조회
    @Override
    public List<PeriodResponseDto> getAllPeriod() {
        List<Period> periods = periodRepository.findAll();

        // Period 엔티티를 PeriodResponseDto로 변환
        List<PeriodResponseDto> periodResponseDtoList = periods.stream()
                .map(period -> PeriodResponseDto.builder()
                        .id(period.getId())
                        .period(period.getPeriod())
                        .status(period.getStatus())
                        .build())
                .toList();

        return periodResponseDtoList;
    }

    // 기수 상태 수정
    @Override
    @Transactional
    public void updatePeriod(Long periodId, PeriodUpdateRequestDto periodUpdateRequestDto) {

        Period period = getPeriodById(periodId);
        period.updatePeriod(periodUpdateRequestDto.getStatus());

        periodRepository.save(period);
    }

    @Override
    @Transactional
    public void deletePeriod(Long periodId) {

        Period period = periodRepository.findByIdAndDeletedIsFalse(periodId).orElseThrow(
                () -> new NoSuchElementException("해당 기수가 존재하지 않습니다.")
        );

        period.delete();
        periodRepository.save(period);
    }

    public Period getPeriodById(Long periodId) {

        return periodRepository.findById(periodId)
                .orElseThrow(() -> new IllegalArgumentException("Period를 찾을수 없습니다.: " + periodId));
    }
}