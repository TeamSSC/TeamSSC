package com.sparta.teamssc.domain.team.repository;

import com.sparta.teamssc.domain.team.entity.Section;
import com.sparta.teamssc.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    // weekProgressId를 통한 삭제되지 않은 팀 찾기
    @Query("SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.userTeamMatches utm LEFT JOIN FETCH utm.user WHERE t.weekProgress.id = :weekProgressId AND t.deleted = false")
    List<Team> findAllByWeekProgressIdAndNotDeleted(@Param("weekProgressId") Long weekProgressId);

    // id를 통한 삭제되지 않은 팀 찾기
    @Query("SELECT t FROM Team t WHERE t.id = :id AND t.deleted = false")
    Optional<Team> findByIdAndNotDeleted(Long id);

    @Query("SELECT DISTINCT t FROM Team t JOIN t.userTeamMatches utm WHERE utm.user.id = :userId AND t.deleted = false")
    List<Team> findTeamsByUserId(@Param("userId") Long userId);

    // weekProgressId와 section을 통한 삭제되지 않은 팀 찾기
    @Query("SELECT DISTINCT t FROM Team t WHERE t.weekProgress.id = :weekProgressId AND t.section = :section AND t.deleted = false")
    List<Team> findAllByWeekProgressIdAndSectionAndNotDeleted(@Param("weekProgressId") Long weekProgressId, @Param("section") Section section);
}
