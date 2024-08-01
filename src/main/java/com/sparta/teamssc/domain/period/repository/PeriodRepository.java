package com.sparta.teamssc.domain.period.repository;

import com.sparta.teamssc.domain.period.dto.PeriodResponseDto;
import com.sparta.teamssc.domain.period.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PeriodRepository extends JpaRepository<Period, Long> {

    Optional<Period> findByIdAndDeletedIsFalse(Long periodId);


    @Query("SELECT new com.sparta.teamssc.domain.period.dto.PeriodResponseDto(p.id, p.period, t.name, p.status) " +
            "FROM Period p JOIN p.track t " +
            "WHERE t.id = :trackId")
    List<PeriodResponseDto> findPeriodDetailsByTrackId(@Param("trackId") Long trackId);
}
