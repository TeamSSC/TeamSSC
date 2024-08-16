package com.sparta.teamssc.domain.image.entity;

import com.sparta.teamssc.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "board_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @JoinColumn(name = "image_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Image image;

    @Builder
    public BoardImage(Board board, Image image) {
        this.board = board;
        this.image = image;
    }
}
