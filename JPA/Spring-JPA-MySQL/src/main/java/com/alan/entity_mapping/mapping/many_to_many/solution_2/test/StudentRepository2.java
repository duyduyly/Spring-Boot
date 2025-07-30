package com.alan.entity_mapping.mapping.many_to_many.solution_2.test;

import com.alan.entity_mapping.mapping.many_to_many.solution_2.Student2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository2 extends JpaRepository<Student2, Long> {}
