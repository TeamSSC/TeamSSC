package com.sparta.teamssc.domain.board.service;

import com.sparta.teamssc.domain.board.dto.response.LikeResponseDto;

public interface LikeService {

    void toggleLikeBoard(Long boardId, String email);

    LikeResponseDto countBoardLikes(Long boardId);
}
