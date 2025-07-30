package com.alan.entity_mapping.mapping.many_to_many.solution_2.test;

import com.alan.entity_mapping.mapping.many_to_many.solution_2.Student2Course2;
import com.alan.entity_mapping.mapping.many_to_many.solution_2.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Student2Course2Repository2 extends JpaRepository<Student2Course2, StudentCourseId> {
    List<Student2Course2> findByStudent2Id(Long studentId);
}
