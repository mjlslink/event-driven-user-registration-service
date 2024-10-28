package org.example.user.db;

import org.example.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegistrationRepository extends JpaRepository<UserEntity, Long> {
}
