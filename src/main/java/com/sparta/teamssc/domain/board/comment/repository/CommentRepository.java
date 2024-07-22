package com.sparta.teamssc.domain.board.comment.repository;

import com.sparta.teamssc.domain.board.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
