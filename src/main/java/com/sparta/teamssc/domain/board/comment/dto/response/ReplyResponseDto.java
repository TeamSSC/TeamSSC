package com.sparta.teamssc.domain.board.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReplyResponseDto {
    private Long commentId;
    private Long parentCommentId;
    private String content;
    private LocalDateTime createAt;

    public ReplyResponseDto(Long commentId, Long parentCommentId, String content, LocalDateTime createAt) {
        this.commentId = commentId;
        this.parentCommentId = parentCommentId;
        this.content = content;
        this.createAt = createAt;
    }
}
