package com.sparta.teamssc.domain.board.comment.repository;

import com.sparta.teamssc.domain.board.comment.dto.response.CommentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {

    Page<CommentResponseDto> findPagedParentCommentList(Long boardId, Pageable pageable);
}
