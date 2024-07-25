package com.sparta.teamssc.domain.board.like.repository;

import com.sparta.teamssc.domain.board.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByBoardIdAndUserId(Long boardId, Long userId);

    List<Like> findByBoardId(Long boardId);
}
