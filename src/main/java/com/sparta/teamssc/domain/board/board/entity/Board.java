package com.sparta.teamssc.domain.board.board.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import com.sparta.teamssc.domain.track.entity.Track;
import com.sparta.teamssc.domain.user.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardType boardType;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "period_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Period period;

    @Builder
    public Board(String title, String content, BoardType boardType, User user, Track track) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.user = user;
        this.track = track;
    }

}
