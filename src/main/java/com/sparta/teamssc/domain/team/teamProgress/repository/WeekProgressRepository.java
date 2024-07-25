package com.sparta.teamssc.domain.team.teamProgress.repository;

import com.sparta.teamssc.domain.team.teamProgress.entity.WeekProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekProgressRepository extends JpaRepository<WeekProgress, Long> {
}
