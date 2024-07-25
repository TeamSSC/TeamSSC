package com.sparta.teamssc.domain.board.board.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardResponseDto {
    private String title;
    private String content;
    private List<String> fileLinks;
}
