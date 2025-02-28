package com.sparta.teamssc.domain.userTeamMatches.repository;

import com.sparta.teamssc.domain.userTeamMatches.entity.UserTeamMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTeamMatchRepository extends JpaRepository<UserTeamMatch, Long> {
}
