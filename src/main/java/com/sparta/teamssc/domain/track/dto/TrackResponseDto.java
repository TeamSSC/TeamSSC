package com.sparta.teamssc.domain.track.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TrackResponseDto {

    private String name;

    @Builder
    public TrackResponseDto(String name) {
        this.name = name;
    }
}
