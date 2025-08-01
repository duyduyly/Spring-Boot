package com.alan.entity_mapping.mapping.many_to_many.solution_1.test;

import com.alan.entity_mapping.mapping.many_to_many.solution_1.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {}
