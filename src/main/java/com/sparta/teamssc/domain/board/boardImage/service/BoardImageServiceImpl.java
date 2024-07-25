package com.sparta.teamssc.domain.board.boardImage.service;

import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.board.boardImage.entity.BoardImage;
import com.sparta.teamssc.domain.board.boardImage.repository.BoardImageRepository;
import com.sparta.teamssc.domain.image.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardImageServiceImpl implements BoardImageService {

    final BoardImageRepository boardImageRepository;

    public void boardImageSave(Board board, Image image) {

        BoardImage boardImage = BoardImage.builder()
                .board(board)
                .image(image)
                .build();

        boardImageRepository.save(boardImage);
    }

}
