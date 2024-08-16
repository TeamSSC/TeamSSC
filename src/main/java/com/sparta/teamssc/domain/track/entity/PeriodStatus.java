package com.sparta.teamssc.domain.track.entity;

public enum PeriodStatus {

    APPLICATION_PERIOD("신청기간인 트랙"),
    IN_PROGRESS("진행중인 트랙"),
    COMPLETED("종료된 트랙"),
    APPLICATION_CLOSED("신청마감된 트랙");

    private final String status;

    PeriodStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
