package com.sparta.teamssc.domain.board.like.service;

import com.sparta.teamssc.domain.board.like.dto.LikeResponseDto;

public interface LikeService {

    void likeBoard(Long boardId, String username);

    void unlikeBoard(Long boardId, String username);

    LikeResponseDto countBoardLikes(Long boardId);
}
