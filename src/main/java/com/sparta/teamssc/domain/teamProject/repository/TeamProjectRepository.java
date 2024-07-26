package com.sparta.teamssc.domain.teamProject.repository;

import com.sparta.teamssc.domain.teamProject.entity.TeamProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamProjectRepository extends JpaRepository<TeamProject, Long> {
    @Query("SELECT tp FROM TeamProject tp WHERE tp.team.id = :teamId AND tp.deleted = false")
    Optional<TeamProject> findByTeamIdAndDeletedFalse(Long teamId);
}