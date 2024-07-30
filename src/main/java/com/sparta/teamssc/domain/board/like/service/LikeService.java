package com.sparta.teamssc.domain.board.like.service;

import com.sparta.teamssc.domain.board.like.dto.LikeResponseDto;

public interface LikeService {

    void likeBoard(Long boardId, String email);

    void unlikeBoard(Long boardId, String email);

    LikeResponseDto countBoardLikes(Long boardId);
}
