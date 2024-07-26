package com.sparta.teamssc.domain.board.board.repository;

import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {

    Page<BoardListResponseDto> findPagedBoardList(Pageable pageable);

    Page<BoardListResponseDto> findPagedNoticeList(Pageable pageable);
}
