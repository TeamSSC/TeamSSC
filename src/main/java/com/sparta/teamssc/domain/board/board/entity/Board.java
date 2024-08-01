package com.sparta.teamssc.domain.board.board.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import com.sparta.teamssc.domain.board.boardImage.entity.BoardImage;
import com.sparta.teamssc.domain.board.comment.entity.Comment;
import com.sparta.teamssc.domain.board.like.entity.Like;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.user.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardImage> boardImages = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @JoinColumn(name = "period_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Period period;

    @Builder
    public Board(String title, String content, BoardType boardType, User user, Period period) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
        this.user = user;
        this.period = period;
    }

    public void updateTile(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}
