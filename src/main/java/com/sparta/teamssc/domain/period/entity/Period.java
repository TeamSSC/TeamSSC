package com.sparta.teamssc.domain.period.entity;

import com.sparta.teamssc.domain.team.entity.Team;
import com.sparta.teamssc.domain.track.entity.Track;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    @OneToMany(mappedBy = "period")
    private List<Team> teams;

    @Enumerated(EnumType.STRING)
    private PeriodStatus status;
}
