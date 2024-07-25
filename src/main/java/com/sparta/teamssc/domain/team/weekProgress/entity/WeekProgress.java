package com.sparta.teamssc.domain.team.weekProgress.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import com.sparta.teamssc.domain.team.entity.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class WeekProgress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "weekProgress")
    private Set<Team> teams;

    @Enumerated(EnumType.STRING)
    private ProgressStatus status;

    // 첫 생성은 진행 혜정 상태의 프로그래스
    @Builder
    public WeekProgress(String name, ProgressStatus status) {
        this.name = name;
        this.status = status;
        this.deleted = false;
    }

    // 예정인 주차는 hardDelete가 되고, 진행중인 주차는 softDelete를 할것입니다.
    @Column(nullable = false)
    private boolean deleted = false;


    // 추후에 프로그래스 수정 가능
    public void updateStatus(ProgressStatus status) {
        this.status = status;
    }

    public void updateName(String name) {
        this.name = name;
    }
    public void delete() {
        this.deleted = true;
    }
}