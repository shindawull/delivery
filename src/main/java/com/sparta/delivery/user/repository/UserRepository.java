package com.sparta.delivery.user.repository;

import com.sparta.delivery.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(@Param("username") @NotBlank @Size(min = 4, max = 20) String username);
    Optional<User> findByUsername(@Param("username") @NotBlank @Size(min = 4, max = 20) String username);
}
