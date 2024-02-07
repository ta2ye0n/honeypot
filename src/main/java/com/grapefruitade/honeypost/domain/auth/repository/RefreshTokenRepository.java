package com.grapefruitade.honeypost.domain.auth.repository;

import com.grapefruitade.honeypost.domain.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
