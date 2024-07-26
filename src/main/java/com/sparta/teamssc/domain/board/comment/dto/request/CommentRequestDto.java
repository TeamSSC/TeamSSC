package com.sparta.teamssc.domain.board.comment.dto.request;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;
    private Long parentCommentId;

    public CommentRequestDto(String content, Long parentCommentId) {
        this.content = content;
        this.parentCommentId = parentCommentId;
    }
}
