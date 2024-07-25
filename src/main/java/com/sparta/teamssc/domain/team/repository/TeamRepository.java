package com.sparta.teamssc.domain.team.repository;

import com.sparta.teamssc.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAllByWeekProgressId(Long weekProgressId);

}
