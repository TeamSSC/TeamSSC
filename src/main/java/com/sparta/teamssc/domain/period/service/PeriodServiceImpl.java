package com.sparta.teamssc.domain.period.service;

import com.sparta.teamssc.domain.period.dto.PeriodRequestDto;
import com.sparta.teamssc.domain.period.dto.PeriodResponseDto;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.period.repository.PeriodRepository;
import com.sparta.teamssc.domain.track.entity.Track;
import com.sparta.teamssc.domain.track.service.TrackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<PeriodResponseDto> getAllPeriod() {
        List<Period> periods = periodRepository.findAll(); // 모든 Period 엔티티 조회

        // Period 엔티티를 PeriodResponseDto로 변환
        List<PeriodResponseDto> periodResponseDtoList = periods.stream()
                .map(period -> PeriodResponseDto.builder()
                        .id(period.getId())
                        .period(period.getPeriod())
                        .status(period.getStatus())
                        .build())
                .toList();

        return periodResponseDtoList; // 변환된 DTO 리스트 반환
    }

    public Period getPeriodById(Long periodId) {

        return periodRepository.findById(periodId)
                .orElseThrow(() -> new IllegalArgumentException("Period를 찾을수 없습니다.: " + periodId));
    }
}