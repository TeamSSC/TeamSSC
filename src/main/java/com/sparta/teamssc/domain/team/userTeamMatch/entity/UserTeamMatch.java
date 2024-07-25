package com.sparta.teamssc.domain.team.userTeamMatch.entity;

import com.sparta.teamssc.domain.team.entity.Team;
import com.sparta.teamssc.domain.user.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class UserTeamMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    public void addUser(User user) {
        this.user = user;
    }

    public void addTeam(Team team) {
        this.team = team;
    }
}
