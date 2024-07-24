package com.sparta.teamssc.domain.board.board.service;

import com.sparta.teamssc.domain.board.board.dto.BoardRequestDto;

public interface BoardService {

    void createBoard(BoardRequestDto requestDto, String username);

}
