package com.sparta.teamssc.domain.board.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BoardResponseDto {
    private String title;
    private String username;
    private LocalDateTime createAt;
    private String content;
    private List<String> fileLinks;
}
