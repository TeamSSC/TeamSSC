package com.sparta.teamssc.domain.board.board.service;

import com.sparta.teamssc.domain.board.board.dto.request.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.dto.request.BoardUpdateRequestDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardResponseDto;
import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.user.role.entity.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {

    void createBoard(BoardRequestDto requestDto, String email);

    BoardResponseDto getBoard(Long boardId);

    Page<BoardListResponseDto> getBoards(int page, String email);

    Board findBoardByBoardId(Long boardId);

    void updateBoard(Long boardId, BoardUpdateRequestDto requestDto, String email/*, List<Role> roles*/);

    void deleteBoard(Long boardId, String username/*, List<Role> roles*/);

    void createNotice(BoardRequestDto requestDto, String email);

    Page<BoardListResponseDto> getNotices(int page, String email);
}
