package com.alan.entity_mapping.mapping.many_to_many.solution_2.test;

import com.alan.entity_mapping.mapping.many_to_many.solution_2.Course2;
import com.alan.entity_mapping.mapping.many_to_many.solution_2.Student2;
import com.alan.entity_mapping.mapping.many_to_many.solution_2.Student2Course2;
import com.alan.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/many-to-many")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController2 {

    private final StudentRepository2 studentRepository2;
    private final CourseRepository2 courseRepository2;
    private final Student2Course2Repository2 student2Course2Repository2;
    private final JsonUtils jsonUtils;

    @GetMapping("/get2")
    public String get() {
        List<Student2Course2> all = student2Course2Repository2.findAll();
        return jsonUtils.convertToJson(this.getStudentMapByStudentId(all));
    }

    @GetMapping("/create2")
    public String create(@RequestParam String studentName, @RequestParam List<String> courseTitleList) {
        Student2 student = new Student2(studentName);
        studentRepository2.save(student);

        Set<Course2> courseSet = courseTitleList.stream().map(Course2::new).collect(Collectors.toSet());
        courseRepository2.saveAll(courseSet);

        Set<Student2Course2> enroll = courseSet.stream().map(course2 -> new Student2Course2(student, course2)).collect(Collectors.toSet());
        List<Student2Course2> student2Course2s = student2Course2Repository2.saveAll(enroll);
        return jsonUtils.convertToJson(this.getStudentMapByStudentId(student2Course2s));
    }

    public Map<String, Set<String>> getStudentMapByStudentId(List<Student2Course2> student2Course2s) {
        return student2Course2s.stream()
                .collect(Collectors.groupingBy(
                        sc -> sc.getStudent2().getName() + sc.getStudent2().getId(),
                        Collectors.mapping(sc -> sc.getCourse2().getTitle(), Collectors.toSet())
                ));
    }


}
