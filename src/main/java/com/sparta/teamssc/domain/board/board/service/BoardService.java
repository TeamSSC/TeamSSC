package com.sparta.teamssc.domain.board.board.service;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.board.board.dto.BoardRequestDto;

public interface BoardService {


    ResponseDto<?> createBoard(BoardRequestDto requestDto, String username);
}
