package com.sparta.teamssc.domain.board.board.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class BoardRequestDto {
    private String title;
    private String content;
    private MultipartFile image;

    public BoardRequestDto(String title, String content, MultipartFile image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
