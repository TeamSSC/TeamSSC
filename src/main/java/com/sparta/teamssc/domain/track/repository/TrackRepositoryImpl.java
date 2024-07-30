package com.sparta.teamssc.domain.track.repository;

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

    public List<TrackResponseDto> findName() {

        QTrack track = QTrack.track;

        List<Track> trackList = jpaQueryFactory.select(track)
                .from(track)
                .where(track.deleted.eq(false))
                .orderBy(track.name.asc())
                .fetch();

        List<TrackResponseDto> trackResponseDtoList = trackList.stream()
                .map(searchTrack -> TrackResponseDto.builder().id(searchTrack.getId()).name(searchTrack.getName()).build())
                .collect(Collectors.toList());

        return trackResponseDtoList;
    }
}
