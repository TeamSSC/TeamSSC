package com.sparta.teamssc.domain.board.repository;

import com.sparta.teamssc.domain.board.dto.response.CommentResponseDto;
import com.sparta.teamssc.domain.board.dto.response.ReplyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {

    Page<CommentResponseDto> findPagedParentCommentList(Long boardId, Pageable pageable);

    Page<ReplyResponseDto> findPagedCommentList(Long parentCommentId, Pageable pageable);
}
