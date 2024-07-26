package com.sparta.teamssc.domain.period.repository;

import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.track.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeriodRepository extends JpaRepository<Period, Long> {
}
