package com.grapefruitade.honeypost.domain.user.repository;

import com.grapefruitade.honeypost.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername (String username);
}
