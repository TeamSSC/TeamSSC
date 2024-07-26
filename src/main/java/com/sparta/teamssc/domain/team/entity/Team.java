package com.sparta.teamssc.domain.team.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.teamProject.entity.TeamProject;
import com.sparta.teamssc.domain.weekProgress.entity.WeekProgress;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.userTeamMatches.entity.UserTeamMatch;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // 빌더 생성자 여러개 생성되기 때문에 전체 엔티티에서 빌더로 수정
@Builder
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

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private TeamProject teamProject;

    @ManyToOne
    @JoinColumn(name = "week_progress_id")
    private WeekProgress weekProgress; // 주차 상태

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    public void addUsers(List<User> users) {
        users.forEach(user -> {
            UserTeamMatch userTeamMatch = UserTeamMatch.builder()
                    .team(this)
                    .user(user)
                    .build();
            this.userTeamMatches.add(userTeamMatch);
        });
    }

    public void updateUsers(List<User> users) {
        userTeamMatches.clear();
        addUsers(users);
    }

    public void delete() {
        this.deleted = true;
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

    public List<User> getUsers() {
        return userTeamMatches.stream()
                .map(UserTeamMatch::getUser)
                .collect(Collectors.toList());
    }

    public void updateWeekProgress(WeekProgress weekProgress) {
        this.weekProgress = weekProgress;
    }

    public void addTeamProject(TeamProject teamProject) {
        this.teamProject = teamProject;
    }
}
