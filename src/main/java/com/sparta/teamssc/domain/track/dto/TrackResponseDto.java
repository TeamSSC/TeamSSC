package com.sparta.teamssc.domain.track.dto;

import lombok.Builder;

public class TrackResponseDto {

    private String name;
    private int batch;
    private String status;

    @Builder
    public TrackResponseDto(String name, int batch, String status) {
        this.name = name;
        this.batch = batch;
        this.status = status;
    }
}
