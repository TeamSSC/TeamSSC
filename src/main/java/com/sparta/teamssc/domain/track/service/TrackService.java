package com.sparta.teamssc.domain.track.service;

import com.sparta.teamssc.domain.track.dto.TrackRequestDto;
import com.sparta.teamssc.domain.track.dto.TrackResponseDto;

import java.util.List;

public interface TrackService {

    TrackResponseDto createTrack(TrackRequestDto trackRequestDto);

    TrackResponseDto updateTrack(Long trackId, TrackRequestDto trackRequestDto);

    List<TrackResponseDto> getTrack(int page, int size);
}
