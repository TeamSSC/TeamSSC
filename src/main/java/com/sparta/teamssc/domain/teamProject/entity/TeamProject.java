package com.sparta.teamssc.domain.teamProject.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import com.sparta.teamssc.domain.team.entity.Team;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamProject extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "project_intro", nullable = false)
    private String projectIntro;

    @Column(name = "notion_link")
    private String notionLink;

    @Column(name = "git_link")
    private String gitLink;

    @Column(name = "figma_link")
    private String figmaLink;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    public void delete() {
        this.deleted = true;
    }
    @Builder
    public TeamProject(Team team, String projectIntro, String notionLink, String gitLink, String figmaLink) {
        this.team = team;
        this.projectIntro = projectIntro;
        this.notionLink = notionLink;
        this.gitLink = gitLink;
        this.figmaLink = figmaLink;
    }

    public void updateProjectIntro(String projectIntro) {
        this.projectIntro = projectIntro;
    }

    public void updateNotionLink(String notionLink) {
        this.notionLink = notionLink;
    }

    public void updateGitLink(String gitLink) {
        this.gitLink = gitLink;
    }

    public void updateFigmaLink(String figmaLink) {
        this.figmaLink = figmaLink;
    }
}