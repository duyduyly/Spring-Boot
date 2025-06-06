package com.alan.security.repositories;

import com.alan.security.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByUsernameAndActiveIsAndOtpVerifyIs(String username, boolean active, boolean acceptIs);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
