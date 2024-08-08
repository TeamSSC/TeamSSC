package com.sparta.teamssc.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    // 엔티티 생성 시점 저장
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    // 엔티티 마지막 수정 시점 저장
    private LocalDateTime updateAt;

    // 한국 시간대에서 현재 시간을 가져오는 메서드
    private LocalDateTime nowInKorea() {
        ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime nowInKorea = ZonedDateTime.now(koreaZoneId);
        return nowInKorea.toLocalDateTime();
    }

    // 엔티티가 저장되기전
    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    // Getter 추가
    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
}
