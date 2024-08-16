package com.sparta.teamssc.domain.track.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "track")
@Getter
@NoArgsConstructor
public class Track extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    // 기수
    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Period> periods;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Builder
    public Track(String name) {
        this.name = name;
        this.deleted = false;
    }

    public Track setTrackName(String name) {

        this.name = name;
        return this;
    }

    public Track deleteTrack() {

        this.deleted=true;
        return this;
    }
}
