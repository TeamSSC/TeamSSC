package com.sparta.teamssc.domain.team.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.team.teamProgress.entity.WeekProgress;
import com.sparta.teamssc.domain.team.userTeamMatch.entity.UserTeamMatch;
import com.sparta.teamssc.domain.user.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기수 아이디
    @ManyToOne
    @JoinColumn(name = "period_id", nullable = false)
    private Period period;

    // 팀이름
    @Column(nullable = true)
    private String teamName;

    // 소속된 반
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Section section;

    // 팀 리더
    @Column(nullable = true)
    private String leaderId;

    // 팀 소개
    @Column(nullable = true)
    private String teamDescription;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<UserTeamMatch> userTeamMatches = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "week_progress_id")
    private WeekProgress weekProgress; // 주차 상태

    @Builder
    public Team(Period period, String teamName, Section section, WeekProgress weekProgress, String leaderId, String teamDescription) {
        this.period = period;
        this.teamName = teamName;
        this.section = section;
        this.weekProgress = weekProgress;
        this.leaderId = leaderId;
        this.teamDescription = teamDescription;
    }

    public void addUsers(List<User> users) {
        users.forEach(user -> {
            UserTeamMatch userTeamMatch = new UserTeamMatch();
            userTeamMatch.addTeam(this);
            userTeamMatch.addUser(user);
            userTeamMatches.add(userTeamMatch);
        });
    }

    public void updatePeriod(Period period) {
        this.period = period;
    }

    public void updateTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void updateTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
    }

    public void updateSection(Section section) {
        this.section = section;
    }

    public void updateUsers(List<User> users) {
        userTeamMatches.clear();
        addUsers(users);
    }

    public List<User> getUsers() {
        return userTeamMatches.stream()
                .map(UserTeamMatch::getUser)
                .collect(Collectors.toList());
    }

    public void updateWeekProgress(WeekProgress weekProgress) {
        this.weekProgress = weekProgress;
    }
}
