package com.sparta.teamssc.domain.userTeamMatches.repository;

import com.sparta.teamssc.domain.userTeamMatches.entity.UserTeamMatches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTeamMatchesRepository extends JpaRepository<UserTeamMatches, Long> {
}
