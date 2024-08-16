package com.sparta.teamssc.domain.board.service;

import com.sparta.teamssc.domain.board.dto.request.BoardRequestDto;
import com.sparta.teamssc.domain.board.dto.request.BoardUpdateRequestDto;
import com.sparta.teamssc.domain.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.board.dto.response.BoardResponseDto;
import com.sparta.teamssc.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public interface BoardService {

    void createBoard(BoardRequestDto requestDto, String email);

    BoardResponseDto getBoard(Long boardId);

    Page<BoardListResponseDto> getBoards(int page, String email);

    Board findBoardByBoardId(Long boardId);

    void updateBoard(Long boardId, BoardUpdateRequestDto requestDto, String email);

    void deleteBoard(Long boardId, String username, List<SimpleGrantedAuthority> authorities);

    void createNotice(BoardRequestDto requestDto, String email);

    Page<BoardListResponseDto> getNotices(int page, String email);
}
