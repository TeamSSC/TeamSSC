//package com.sparta.teamssc.domain.team.userTeamMatch.entity;
//
//import com.sparta.teamssc.domain.team.entity.Team;
//import com.sparta.teamssc.domain.user.user.entity.User;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "user_team_match")
//public class UserTeamMatch {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "team_id", nullable = false)
//    private Team team;
//
//
//    public void addUser(User user) {
//        this.user = user;
//    }
//
//    public void addTeam(Team team) {
//        this.team = team;
//    }
//
//    @Builder
//    public UserTeamMatch(User user, Team team) {
//        this.user = user;
//        this.team = team;
//    }
//}
