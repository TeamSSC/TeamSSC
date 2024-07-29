package com.sparta.teamssc.domain.period.entity;

import com.sparta.teamssc.domain.track.entity.Track;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Period")
@NoArgsConstructor
@Getter
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int period;

    @ManyToOne
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    @Enumerated(EnumType.STRING)
    private PeriodStatus status;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Builder
    public Period(int period, PeriodStatus status, Track track) {
        this.track = track;
        this.period = period;
        this.status = status;
    }

    public void updatePeriod(PeriodStatus status) {
        this.status = status;
    }

    public void delete() {
        this.deleted = true;
    }
}
