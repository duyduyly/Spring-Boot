package com.alan.entity_mapping.mapping.many_to_many.solution_2;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseId implements Serializable {
    private Long studentId;
    private Long courseId;
}
