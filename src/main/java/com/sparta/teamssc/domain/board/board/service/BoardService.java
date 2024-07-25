package com.sparta.teamssc.domain.board.board.service;

import com.sparta.teamssc.domain.board.board.dto.request.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardResponseDto;
import org.springframework.data.domain.Page;

public interface BoardService {

    void createBoard(BoardRequestDto requestDto, String username);

    BoardResponseDto getBoard(Long boardId);

    Page<BoardListResponseDto> getBoards(int page);
}
