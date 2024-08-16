package com.sparta.teamssc.domain.board.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardListResponseDto {
    private Long boardId;
    private String title;
    private String username;
    private LocalDateTime createAt;

    @Builder
    public BoardListResponseDto (Long boardId, String title, String username, LocalDateTime createAt) {
        this.boardId = boardId;
        this.title = title;
        this.username = username;
        this.createAt = createAt;
    }
}
