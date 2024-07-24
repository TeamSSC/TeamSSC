package com.sparta.teamssc.domain.image.repository;

import com.sparta.teamssc.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByFileLink(String url);
}
