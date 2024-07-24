package com.sparta.teamssc.domain.period.service;

import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.period.repository.PeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeriodServiceImpl implements PeriodService {

    private final PeriodRepository periodRepository;

    public Period getPeriodById(Long periodId) {
        return periodRepository.findById(periodId)
                .orElseThrow(() -> new IllegalArgumentException("Period를 찾을수 없습니다.: " + periodId));
    }
}