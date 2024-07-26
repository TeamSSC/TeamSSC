package com.sparta.teamssc.domain.board.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeResponseDto {
    private Long boardId;
    private int likeCount;

    @Builder
    public LikeResponseDto(Long boardId, int likeCount) {
        this.boardId = boardId;
        this.likeCount = likeCount;
    }
}
