package com.sparta.teamssc.domain.team.weekProgress.repository;

import com.sparta.teamssc.domain.team.weekProgress.entity.WeekProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WeekProgressRepository extends JpaRepository<WeekProgress, Long> {

    // id를 통한 삭제되지 않는 프로그래스
    @Query("SELECT wp FROM WeekProgress wp WHERE wp.id = :id AND wp.deleted = false")
    Optional<WeekProgress> findByIdAndNotDeleted(Long id);

    // 삭제되지 않는 전체 프로그래스
    @Query("SELECT wp FROM WeekProgress wp WHERE wp.deleted = false")
    List<WeekProgress> findByNotDeleted();

    // 전체 주차 페이징으로 불러오기
    @Query("SELECT wp FROM WeekProgress wp WHERE wp.deleted = false")
    Page<WeekProgress> findByNotDeleted(Pageable pageable);
}
