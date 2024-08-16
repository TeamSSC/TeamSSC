package com.sparta.teamssc.domain.track.service;

import com.sparta.teamssc.domain.track.dto.request.TrackRequestDto;
import com.sparta.teamssc.domain.track.dto.response.TrackResponseDto;
import com.sparta.teamssc.domain.track.entity.Track;

import java.util.List;

public interface TrackService {

    TrackResponseDto createTrack(TrackRequestDto trackRequestDto);

    TrackResponseDto updateTrack(Long trackId, TrackRequestDto trackRequestDto);

    List<TrackResponseDto> getTracks();

    TrackResponseDto getTrack(Long trackId);

    Track searchTrack(Long trackId);

    void deleteTrack(Long trackId);
}
