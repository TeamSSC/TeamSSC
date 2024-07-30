package com.sparta.teamssc.domain.board.board.repository;

import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.period.entity.Period;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {

    Page<BoardListResponseDto> findPagedBoardList(Pageable pageable, Period period);

    Page<BoardListResponseDto> findPagedNoticeList(Pageable pageable, Period period);
}
