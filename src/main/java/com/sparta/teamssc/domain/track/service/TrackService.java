package com.sparta.teamssc.domain.track.service;

import com.sparta.teamssc.domain.track.dto.TrackRequestDto;
import com.sparta.teamssc.domain.track.dto.TrackResponseDto;

public interface TrackService {

    TrackResponseDto createTrack(TrackRequestDto trackRequestDto);

    TrackResponseDto updateTrack(Long trackId, TrackRequestDto trackRequestDto);
}
