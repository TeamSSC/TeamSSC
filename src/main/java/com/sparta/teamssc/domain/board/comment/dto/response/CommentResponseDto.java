package com.sparta.teamssc.domain.board.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private LocalDateTime createAt;
    private String username;

    public CommentResponseDto(Long commentId, String content, LocalDateTime createAt, String username) {
        this.commentId = commentId;
        this.content = content;
        this.createAt = createAt;
        this.username = username;
    }
}
