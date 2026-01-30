package com.example.initialize.initialize_java_8_project.repository;

import com.example.initialize.initialize_java_8_project.model.entity.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DemoRepository extends JpaRepository<DemoEntity, Long> {

    List<DemoEntity> findAllByDeletedFalse();

    Optional<DemoEntity> findByIdAndDeletedFalse(Long id);
}
