package com.sparta.teamssc.domain.track.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TrackResponseDto {

    private Long id;
    private String name;

    @Builder
    public TrackResponseDto(Long id, String name) {

        this.id = id;
        this.name = name;

    }
}
