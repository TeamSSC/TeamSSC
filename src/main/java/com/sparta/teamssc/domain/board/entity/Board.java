package com.sparta.teamssc.domain.board.entity;

import com.sparta.teamssc.domain.common.entity.BaseEntity;
import com.sparta.teamssc.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.sound.midi.Track;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String title;

    private String content;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

//    @JoinColumn(name = "track_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Track track;
//
//    @Builder
//    public Board(String title, String content, User user, Track track) {
//        this.title = title;
//        this.content = content;
//        this.user = user;
//        this.track = track;
//    }

}