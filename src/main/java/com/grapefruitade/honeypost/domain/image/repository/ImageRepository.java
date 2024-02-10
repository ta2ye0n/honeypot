package com.grapefruitade.honeypost.domain.image.repository;

import com.grapefruitade.honeypost.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPostId(Long id);
}
