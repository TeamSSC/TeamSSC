package com.sparta.teamssc.domain.track.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "track")
@Getter
@NoArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Long id;

    // 기수
    @Column(nullable = false)
    private int batch;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private statusEnum status;

}