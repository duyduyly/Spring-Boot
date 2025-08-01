package com.alan.entity_mapping.mapping.many_to_many.solution_2.test;

import com.alan.entity_mapping.mapping.many_to_many.solution_2.Course2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository2 extends JpaRepository<Course2, Long> {}
