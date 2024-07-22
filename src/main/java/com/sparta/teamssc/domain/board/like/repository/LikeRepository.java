package com.sparta.teamssc.domain.board.like.repository;

import com.sparta.teamssc.domain.board.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
