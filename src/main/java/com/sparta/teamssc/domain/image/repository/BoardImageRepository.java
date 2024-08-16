package com.sparta.teamssc.domain.image.repository;

import com.sparta.teamssc.domain.image.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

    List<BoardImage> findByBoardId(Long boardId);
}
