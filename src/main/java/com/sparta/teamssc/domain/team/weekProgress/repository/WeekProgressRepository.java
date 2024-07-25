package com.sparta.teamssc.domain.team.weekProgress.repository;

import com.sparta.teamssc.domain.team.weekProgress.entity.WeekProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekProgressRepository extends JpaRepository<WeekProgress, Long> {
}
