package com.sparta.teamssc.domain.track.repository;

import com.sparta.teamssc.domain.track.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
}
