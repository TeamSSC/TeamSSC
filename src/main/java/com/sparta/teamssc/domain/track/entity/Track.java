package com.sparta.teamssc.domain.track.entity;

import com.sparta.teamssc.domain.period.entity.Period;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "track")
@Getter
@NoArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    // 기수
    @OneToMany(mappedBy = "track")
    private List<Period> periods;

    @Builder
    public Track(String name) {
        this.name = name;
    }
}
