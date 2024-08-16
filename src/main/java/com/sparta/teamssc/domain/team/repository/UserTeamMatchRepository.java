package com.sparta.teamssc.domain.team.repository;

import com.sparta.teamssc.domain.team.entity.UserTeamMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTeamMatchRepository extends JpaRepository<UserTeamMatch, Long> {
}
