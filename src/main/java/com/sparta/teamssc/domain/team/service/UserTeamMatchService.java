package com.sparta.teamssc.domain.team.service;

import com.sparta.teamssc.domain.team.entity.Team;
import com.sparta.teamssc.domain.user.entity.User;
import com.sparta.teamssc.domain.team.entity.UserTeamMatch;

public interface UserTeamMatchService {
    void save(UserTeamMatch userTeamMatch);

    UserTeamMatch create(User user, Team team);

    void delete(UserTeamMatch match);
}
