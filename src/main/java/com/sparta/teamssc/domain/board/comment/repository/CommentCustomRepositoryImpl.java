package com.sparta.teamssc.domain.board.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.teamssc.domain.board.comment.dto.response.CommentResponseDto;
import com.sparta.teamssc.domain.board.comment.dto.response.ReplyResponseDto;
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

        List<CommentResponseDto> parentCommentList =jpaQueryFactory
                .select(Projections.constructor(CommentResponseDto.class,
                        qComment.id,
                        qComment.content,
                        qComment.createAt,
                        qComment.user.username
                ))
                .from(qComment)
                .orderBy(qComment.createAt.desc())
                .where(qComment.parentCommentId.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(parentCommentList, pageable, parentCommentList.size());
    }

    @Override
    public Page<ReplyResponseDto> findPagedCommentList(Long parentCommentId, Pageable pageable) {
        QComment qComment = QComment.comment;

        List<ReplyResponseDto> commentList =jpaQueryFactory
                .select(Projections.constructor(ReplyResponseDto.class,
                        qComment.id,
                        qComment.parentCommentId,
                        qComment.content,
                        qComment.createAt,
                        qComment.user.username
                ))
                .from(qComment)
                .orderBy(qComment.createAt.desc())
                .where(qComment.parentCommentId.isNotNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(commentList, pageable, commentList.size());
    }
}
