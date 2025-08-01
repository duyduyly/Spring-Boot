package com.alan.entity_mapping.mapping.many_to_many.solution_1.test;

import com.alan.entity_mapping.mapping.many_to_many.solution_1.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {}
