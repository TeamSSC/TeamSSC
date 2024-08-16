package com.sparta.teamssc.domain.image.service;

import com.sparta.teamssc.domain.board.entity.Board;
import com.sparta.teamssc.domain.image.entity.Image;

import java.util.List;

public interface BoardImageService {

    void boardImageSave(Board board, Image image);

    List<String> findFileUrlByBoardId(Long boardId);
}
