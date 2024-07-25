package com.sparta.teamssc.domain.board.board.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardListResponseDto {
    private Long boardId;
    private String title;
    private LocalDateTime createAt;

    @Builder
    public BoardListResponseDto (Long boardId, String title, LocalDateTime createAt) {
        this.boardId = boardId;
        this.title = title;
        this.createAt = createAt;
    }
}
