package com.sparta.teamssc.domain.board.board.service;

import com.sparta.teamssc.domain.board.board.dto.request.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.dto.request.BoardUpdateRequestDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardResponseDto;
import com.sparta.teamssc.domain.board.board.entity.Board;
import org.springframework.data.domain.Page;

public interface BoardService {

    void createBoard(BoardRequestDto requestDto, String username);

    BoardResponseDto getBoard(Long boardId);

    Page<BoardListResponseDto> getBoards(int page);

    Board findBoardByBoardId(Long boardId);

    void updateBoard(Long boardId, BoardUpdateRequestDto requestDto, String username);

    void deleteBoard(Long boardId, String username);
}
