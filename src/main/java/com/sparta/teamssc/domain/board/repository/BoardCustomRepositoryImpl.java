package com.sparta.teamssc.domain.board.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.teamssc.domain.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.board.entity.BoardType;
import com.sparta.teamssc.domain.board.board.entity.QBoard;
import com.sparta.teamssc.domain.track.entity.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<BoardListResponseDto> findPagedBoardList(Pageable pageable, Period period) {
        QBoard qBoard = QBoard.board;

        List<BoardListResponseDto> boardList = jpaQueryFactory
                .select(Projections.constructor(BoardListResponseDto.class,
                        qBoard.id,
                        qBoard.title,
                        qBoard.user.username,
                        qBoard.createAt
                ))
                .from(qBoard)
                .orderBy(qBoard.createAt.desc())
                .where(qBoard.boardType.eq(BoardType.BOARD), qBoard.period.eq(period))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(boardList, pageable, boardList.size());
    }

    @Override
    public Page<BoardListResponseDto> findPagedNoticeList(Pageable pageable, Period period) {
        QBoard qBoard = QBoard.board;

        List<BoardListResponseDto> boardList = jpaQueryFactory
                .select(Projections.constructor(BoardListResponseDto.class,
                        qBoard.id,
                        qBoard.title,
                        qBoard.user.username,
                        qBoard.createAt
                ))
                .from(qBoard)
                .orderBy(qBoard.createAt.desc())
                .where(qBoard.boardType.eq(BoardType.NOTICE), qBoard.period.eq(period))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(boardList, pageable, boardList.size());
    }
}
