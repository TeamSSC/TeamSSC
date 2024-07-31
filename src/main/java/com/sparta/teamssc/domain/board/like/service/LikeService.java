package com.sparta.teamssc.domain.board.like.service;

import com.sparta.teamssc.domain.board.like.dto.LikeResponseDto;

public interface LikeService {

    void toggleLikeBoard(Long boardId, String email);

    LikeResponseDto countBoardLikes(Long boardId);
}
