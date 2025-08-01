package com.alan.entity_listener.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Model2Repository extends JpaRepository<Model2, Long> {

}
