package com.sparta.teamssc.domain.board.entity;

import com.sparta.teamssc.common.entity.BaseEntity;
import com.sparta.teamssc.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(nullable = true)
    private Long parentCommentId;

    @Builder
    public Comment (User user, Board board, String content, Long parentCommentId) {
        this.user = user;
        this.board = board;
        this.content = content;
        this.parentCommentId = parentCommentId;
    }

    public void update(String content) {
        this.content = content;
    }

}
