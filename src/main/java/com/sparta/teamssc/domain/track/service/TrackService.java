package com.sparta.teamssc.domain.track.service;

import com.sparta.teamssc.domain.track.dto.TrackRequestDto;
import com.sparta.teamssc.domain.track.dto.TrackResponseDto;
import com.sparta.teamssc.domain.track.entity.Track;

import java.util.List;

public interface TrackService {

    TrackResponseDto createTrack(TrackRequestDto trackRequestDto);

    TrackResponseDto updateTrack(Long trackId, TrackRequestDto trackRequestDto);

    List<TrackResponseDto> getTracks(int page, int size);

    TrackResponseDto getTrack(Long trackId);

    Track searchTrack(Long trackId);
}
