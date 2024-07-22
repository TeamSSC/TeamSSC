package com.sparta.teamssc.domain.team.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.sound.midi.Track;

@Entity
@Getter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "team_name", nullable = false)
    private String teamName;

//
//    @JoinColumn(name = "track_id", nullable = false)
//    @ManyToOne
//    private Track track;
//
//    @Builder
//    public Team(String teamName, Track track) {
//        this.teamName = teamName;
//        this.track = track;
//    }

}
