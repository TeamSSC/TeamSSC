package com.sparta.teamssc.domain.userTeamMatches.entity;

import com.sparta.teamssc.domain.team.entity.Team;
import com.sparta.teamssc.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_team_matches")
public class UserTeamMatches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "team_id", nullable = false)
    @ManyToOne
    private Team team;

    @Builder
    public UserTeamMatches(User user, Team team) {
        this.user = user;
        this.team = team;
    }

}
