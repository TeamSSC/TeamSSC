package com.sparta.teamssc.domain.userTeamMatches.service;

import com.sparta.teamssc.domain.team.entity.Team;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.userTeamMatches.entity.UserTeamMatch;
import com.sparta.teamssc.domain.userTeamMatches.repository.UserTeamMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTeamMatchServiceImpl implements UserTeamMatchService {
    private final UserTeamMatchRepository userTeamMatchRepository;

    @Override
    public void save(UserTeamMatch userTeamMatch) {
        try {
            userTeamMatchRepository.save(userTeamMatch);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("UserTeamMatch 생성에 실패했습니다.");
        }
    }

    @Override
    public UserTeamMatch create(User user, Team team) {
        return UserTeamMatch.builder()
                .user(user)
                .team(team)
                .build();
    }

    @Override
    public void delete(UserTeamMatch match) {
        userTeamMatchRepository.delete(match);
    }
}