package com.alan.entity_mapping.mapping.one_to_one.test;

import com.alan.entity_mapping.mapping.one_to_one.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
