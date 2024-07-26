package com.sparta.teamssc.domain.track.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.teamssc.domain.track.dto.TrackResponseDto;
import com.sparta.teamssc.domain.track.entity.QTrack;
import com.sparta.teamssc.domain.track.entity.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TrackRepositoryImpl implements TrackRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<TrackResponseDto> findName(long offset, int pageSize) {

        QTrack track = QTrack.track;

        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.ASC, track.name);

        List<Track> trackList = jpaQueryFactory.select(track)
                                .from(track)
                                .offset(offset)
                                .limit(pageSize)
                                .orderBy(orderSpecifier)
                                .fetch();

        List<TrackResponseDto> trackResponseDtoList = trackList.stream()
                .map(searchTrack -> TrackResponseDto.builder().name(searchTrack.getName()).build())
                .collect(Collectors.toList());

        return trackResponseDtoList;
    }
}
