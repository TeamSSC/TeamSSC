package com.sparta.teamssc.domain.user.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.user.user.dto.response.PendSignupResponseDto;
import com.sparta.teamssc.domain.user.user.entity.QUser;
import com.sparta.teamssc.domain.user.user.entity.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PendSignupResponseDto> findPagedPendList(Pageable pageable, Period period) {
        QUser qUser = QUser.user;

        List<PendSignupResponseDto> pendList = jpaQueryFactory
                .select(Projections.constructor(PendSignupResponseDto.class,
                        qUser.id,
                        qUser.email,
                        qUser.username,
                        qUser.status,
                        qUser.createAt
                ))
                .from(qUser)
                .orderBy(qUser.createAt.desc())
                .where(qUser.status.eq(UserStatus.PENDING))
                .where(qUser.period.eq(period))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(pendList, pageable, pendList.size());
    }
}
