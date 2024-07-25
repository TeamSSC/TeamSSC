package com.sparta.teamssc.domain.board.board.dto.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class BoardUpdateRequestDto {
    private String title;
    private String content;
    private List<String> deleteImagesLink;
    private List<MultipartFile> uploadImages;

    public BoardUpdateRequestDto(String title, String content, List<String> deleteImagesLink, List<MultipartFile> uploadImages) {
        this.title = title;
        this.content = content;
        this.deleteImagesLink = deleteImagesLink;
        this.uploadImages = uploadImages;
    }
}
