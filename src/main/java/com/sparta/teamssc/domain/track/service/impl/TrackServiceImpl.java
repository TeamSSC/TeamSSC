package com.sparta.teamssc.domain.track.service.impl;

import com.sparta.teamssc.domain.track.dto.request.TrackRequestDto;
import com.sparta.teamssc.domain.track.dto.response.TrackResponseDto;
import com.sparta.teamssc.domain.track.entity.Track;
import com.sparta.teamssc.domain.track.repository.TrackRepository;
import com.sparta.teamssc.domain.track.service.TrackService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    public TrackResponseDto createTrack(TrackRequestDto trackRequestDto) {

        Track track = Track.builder()
                .name(trackRequestDto.getName())
                .build();

        trackRepository.save(track);

        return TrackResponseDto.builder()
                .id(track.getId())
                .name(track.getName())
                .build();

    }

    @Transactional
    public TrackResponseDto updateTrack(Long trackId, TrackRequestDto trackRequestDto) {

        Track track = trackRepository.findByIdAndDeletedIsFalse(trackId).orElseThrow(
                ()-> new NoSuchElementException("수정할 트랙을 찾지 못했습니다.")
        );

        if (track.getName().equals(trackRequestDto.getName())) {
            throw new IllegalArgumentException("트랙명을 같은 이름으로 변경할 수 없습니다.");
        }

        if (trackRepository.findByName(trackRequestDto.getName()).isPresent()) {
            throw new DuplicateRequestException("중복된 트랙명이 존재합니다.");
        }

        Track updatedTrack = track.setTrackName(trackRequestDto.getName());

        return TrackResponseDto.builder()
                .id(track.getId())
                .name(updatedTrack.getName())
                .build();
    }

    public TrackResponseDto getTrack(Long trackId) {

        Track track = trackRepository.findByIdAndDeletedIsFalse(trackId).orElseThrow(
                ()-> new NoSuchElementException("요청하신 트랙이 존재하지 않습니다.")
        );

        return TrackResponseDto.builder()
                .id(track.getId())
                .name(track.getName())
                .build();
    }

    public List<TrackResponseDto> getTracks() {

        return trackRepository.findName();
    }

    @Transactional
    public void deleteTrack(Long trackId) {

        Track track = trackRepository.findByIdAndDeletedIsFalse(trackId).orElseThrow(
                ()-> new NoSuchElementException("해당 트랙이 존재하지 않습니다.")
        );

        track.deleteTrack();

        // TODO : 기수에 deleted 필드 추가되면 연관된 기수도 모두 Soft delete 처리로직 추가 예정
    }

    public Track searchTrack(Long trackId) {

        return trackRepository.findByIdAndDeletedIsFalse(trackId).orElseThrow(
                ()-> new NoSuchElementException("요청하신 트랙이 존재하지 않습니다.")
        );
    }
}