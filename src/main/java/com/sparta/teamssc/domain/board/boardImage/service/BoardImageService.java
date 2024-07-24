package com.sparta.teamssc.domain.board.boardImage.service;

import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.image.entity.Image;

public interface BoardImageService {

    void boardImageSave(Board board, Image image);

}
