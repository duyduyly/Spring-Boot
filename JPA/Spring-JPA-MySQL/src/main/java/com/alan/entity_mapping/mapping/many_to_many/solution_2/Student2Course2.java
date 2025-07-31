package com.alan.entity_mapping.mapping.many_to_many.solution_2;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Entity
@Table(name = "STUDENT2_COURSE2", indexes = @Index(name = "idx_student2_course2", columnList = "student2_id, course2_id"))
@EqualsAndHashCode(exclude = {"student2", "course2"})
@ToString(exclude = {"student2", "course2"})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Student2Course2 {

    @EmbeddedId
    private StudentCourseId id = new StudentCourseId();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @MapsId("studentId") //Mapping with key in StudentCourseId
    private Student2 student2;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @MapsId("courseId") //Mapping with key in StudentCourseId
    private Course2 course2;

    @Builder.Default
    private LocalDate enrollDate = LocalDate.now(); // Optional custom field

    public Student2Course2(Student2 student2, Course2 course2) {
        this.student2 = student2;
        this.course2 = course2;
        this.id = new StudentCourseId(student2.getId(), course2.getId());
    }
}
