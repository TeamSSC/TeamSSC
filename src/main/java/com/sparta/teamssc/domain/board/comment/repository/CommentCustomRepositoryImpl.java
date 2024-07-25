package com.sparta.teamssc.domain.board.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.teamssc.domain.board.comment.dto.response.CommentResponseDto;
import com.sparta.teamssc.domain.board.comment.entity.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CommentResponseDto> findPagedParentCommentList(Long boardId, Pageable pageable) {
        QComment qComment = QComment.comment;

        List<CommentResponseDto> commentList =jpaQueryFactory
                .select(Projections.constructor(CommentResponseDto.class,
                        qComment.id,
                        qComment.content,
                        qComment.createAt
                ))
                .from(qComment)
                .orderBy(qComment.createAt.desc())
                .where(qComment.parentCommentId.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(commentList, pageable, commentList.size());
    }
}
