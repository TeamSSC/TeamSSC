package com.sparta.teamssc.domain.board.board.service;

import com.sparta.teamssc.domain.board.board.dto.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.dto.BoardResponseDto;

public interface BoardService {

    void createBoard(BoardRequestDto requestDto, String username);

    BoardResponseDto getBoard(Long boardId);
}
