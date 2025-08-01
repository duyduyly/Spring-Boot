package com.alan.entity_mapping.mapping.one_to_one.test;

import com.alan.entity_mapping.mapping.one_to_one.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
