package com.sparta.teamssc.domain.board.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCommentRequestDto {
    private String content;

    public UpdateCommentRequestDto(String content) {
        this.content = content;
    }
}