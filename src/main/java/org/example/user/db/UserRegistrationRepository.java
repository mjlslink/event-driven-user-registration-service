package org.example.user.db;

import org.example.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRegistrationRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String username);
}
