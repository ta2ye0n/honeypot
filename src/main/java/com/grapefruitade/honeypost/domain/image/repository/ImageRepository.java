package com.grapefruitade.honeypost.domain.image.repository;

import com.grapefruitade.honeypost.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {


}
