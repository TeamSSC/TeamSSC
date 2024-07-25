package com.sparta.teamssc.domain.board.comment.service;

import com.sparta.teamssc.domain.board.comment.dto.request.CommentRequestDto;

public interface CommentService {

    void createComment(Long boardId, CommentRequestDto commentRequestDto, String username);
}