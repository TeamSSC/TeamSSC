package com.sparta.teamssc.domain.board.comment.service;

import com.sparta.teamssc.domain.board.comment.dto.request.CommentRequestDto;
import com.sparta.teamssc.domain.board.comment.dto.request.UpdateCommentRequestDto;
import com.sparta.teamssc.domain.board.comment.dto.response.CommentResponseDto;
import com.sparta.teamssc.domain.board.comment.dto.response.ReplyResponseDto;
import org.springframework.data.domain.Page;

public interface CommentService {

    void createComment(Long boardId, CommentRequestDto commentRequestDto, String email);

    Page<CommentResponseDto> getCommentFromBoard(Long boardId, int page);

    Page<ReplyResponseDto> getCommentFromParentComment(Long parentCommentId, int page);

    void updateComment(Long commentId, UpdateCommentRequestDto requestDto, String email);
}