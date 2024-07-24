package com.sparta.teamssc.domain.board.like.entity;

import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.user.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "board_id", nullable = false)
    @ManyToOne
    private Board board;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    @Builder
    public Like(Board board, User user) {
        this.board = board;
        this.user = user;
    }

}
