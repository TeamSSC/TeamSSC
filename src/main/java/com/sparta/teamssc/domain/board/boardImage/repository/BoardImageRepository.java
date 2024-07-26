package com.sparta.teamssc.domain.board.boardImage.repository;

import com.sparta.teamssc.domain.board.boardImage.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

    List<BoardImage> findByBoardId(Long boardId);
}
