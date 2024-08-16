package com.sparta.teamssc.domain.track.repository;

import com.sparta.teamssc.domain.track.dto.response.TrackResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepositoryCustom {
    List<TrackResponseDto> findName();
}
