package com.sparta.teamssc.domain.period.service;

import com.sparta.teamssc.domain.period.dto.PeriodRequestDto;
import com.sparta.teamssc.domain.period.dto.PeriodResponseDto;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.period.repository.PeriodRepository;
import com.sparta.teamssc.domain.track.entity.Track;
import com.sparta.teamssc.domain.track.service.TrackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Period getPeriodById(Long periodId) {

        return periodRepository.findById(periodId)
                .orElseThrow(() -> new IllegalArgumentException("Period를 찾을수 없습니다.: " + periodId));
    }
}