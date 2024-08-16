package com.sparta.teamssc.domain.track.service.impl;

import com.sparta.teamssc.domain.track.dto.request.PeriodRequestDto;
import com.sparta.teamssc.domain.track.dto.response.PeriodResponseDto;
import com.sparta.teamssc.domain.track.dto.request.PeriodUpdateRequestDto;
import com.sparta.teamssc.domain.track.entity.Period;
import com.sparta.teamssc.domain.track.repository.PeriodRepository;
import com.sparta.teamssc.domain.track.entity.Track;
import com.sparta.teamssc.domain.track.service.PeriodService;
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

        // fix : 기수 생성 시 기수번호를 사용자에게 입력받지 않고, DB에 저장된 기수를 체크하여 기수 자동증가하도록 변경
        int periodNumber = periodRepository.findCountByTrack(track) + 1;

        Period period = Period.builder()
        .track(track)
        .period(periodNumber)
        .status(periodRequestDto.getStatus())
        .build();

        periodRepository.save(period);

        return PeriodResponseDto.builder()
                .id(track.getId())
                .period(periodNumber)
                .trackName(track.getName())
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
                        .trackName(period.getTrack().getName())
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

    // 기수 삭제
    @Override
    @Transactional
    public void deletePeriod(Long periodId) {

        Period period = periodRepository.findByIdAndDeletedIsFalse(periodId).orElseThrow(
                () -> new NoSuchElementException("해당 기수가 존재하지 않습니다.")
        );

        period.delete();
        periodRepository.save(period);
    }

    //트랙에 대한 기수 조회
    @Override
    @Transactional
    public List<PeriodResponseDto> getTrackByPeriod(Long trackId){
        return periodRepository.findPeriodDetailsByTrackId(trackId);
    }

    public Period getPeriodById(Long periodId) {

        return periodRepository.findById(periodId)
                .orElseThrow(() -> new IllegalArgumentException("Period를 찾을수 없습니다.: " + periodId));
    }

    @Override
    public boolean isUserInPeriod(Long userId, Long periodId) {
        Period period = periodRepository.findById(periodId).orElse(null);
        if (period == null) {
            throw new IllegalArgumentException("해당 기간이 존재하지 않습니다.");
        }
        return period.getUsers().stream()
                .anyMatch(user -> user.getId().equals(userId));
    }
}