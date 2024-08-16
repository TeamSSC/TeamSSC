package com.sparta.teamssc.domain.image.service.impl;

import com.sparta.teamssc.domain.board.entity.Board;
import com.sparta.teamssc.domain.image.entity.BoardImage;
import com.sparta.teamssc.domain.image.repository.BoardImageRepository;
import com.sparta.teamssc.domain.image.entity.Image;
import com.sparta.teamssc.domain.image.service.BoardImageService;
import com.sparta.teamssc.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardImageServiceImpl implements BoardImageService {

    final BoardImageRepository boardImageRepository;
    final ImageService imageService;

    @Override
    public void boardImageSave(Board board, Image image) {

        BoardImage boardImage = BoardImage.builder()
                .board(board)
                .image(image)
                .build();

        boardImageRepository.save(boardImage);
    }

    @Override
    public List<String> findFileUrlByBoardId(Long boardId) {

        List<BoardImage> boardImageList = boardImageRepository.findByBoardId(boardId);
        List<String> fileUrlList = new ArrayList<>();
        for (BoardImage boardImage : boardImageList) {
            fileUrlList.add(imageService.findFileUrlByImageId(boardImage.getImage().getId()));
        }
        return fileUrlList;
    }

}
