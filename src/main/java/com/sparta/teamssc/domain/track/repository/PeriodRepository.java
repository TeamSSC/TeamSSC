package com.sparta.teamssc.domain.track.repository;

import com.sparta.teamssc.domain.track.dto.response.PeriodResponseDto;
import com.sparta.teamssc.domain.track.entity.Period;
import com.sparta.teamssc.domain.track.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PeriodRepository extends JpaRepository<Period, Long> {

    Optional<Period> findByIdAndDeletedIsFalse(Long periodId);

    @Query("SELECT new com.sparta.teamssc.domain.track.dto.response.PeriodResponseDto(p.id, p.period, t.name, p.status) " +
            "FROM Period p JOIN p.track t " +
            "WHERE t.id = :trackId AND p.deleted = false ")
    List<PeriodResponseDto> findPeriodDetailsByTrackId(@Param("trackId") Long trackId);

    @Query("SELECT COUNT(*) FROM Period P WHERE P.track = :track")
    int findCountByTrack(@Param("track") Track track);
}