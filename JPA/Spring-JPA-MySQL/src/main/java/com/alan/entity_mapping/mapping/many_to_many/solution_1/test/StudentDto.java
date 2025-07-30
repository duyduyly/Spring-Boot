package com.alan.entity_mapping.mapping.many_to_many.solution_1.test;

import com.alan.entity_mapping.mapping.many_to_many.solution_1.Course;
import com.alan.entity_mapping.mapping.many_to_many.solution_1.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDto {

    @JsonProperty("student_name")
    private String studentName;

    @JsonProperty("course_name_List")
    private Set<String> courseNameSet;


    public StudentDto(Student student) {
        this.studentName = student.getName();
        if (Objects.nonNull(student.getCourses())) {
            this.courseNameSet = student.getCourses().stream().map(Course::getTitle).collect(Collectors.toSet());
        }
    }
}
