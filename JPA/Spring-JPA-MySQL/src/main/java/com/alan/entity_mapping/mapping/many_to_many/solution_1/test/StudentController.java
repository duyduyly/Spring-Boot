package com.alan.entity_mapping.mapping.many_to_many.solution_1.test;

import com.alan.entity_mapping.mapping.many_to_many.solution_1.Course;
import com.alan.entity_mapping.mapping.many_to_many.solution_1.Student;
import com.alan.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/many-to-many")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final JsonUtils jsonUtils;

    @GetMapping("/get")
    public String get() {
        List<Student> studentList = studentRepository.findAll();
        List<Course> courseList = courseRepository.findAll();

        List<StudentDto> studentDtoList = studentList.stream().map(StudentDto::new).collect(Collectors.toList());
        String result1 = jsonUtils.convertToJson(studentDtoList);
        String result2 = jsonUtils.convertToJson(this.getCourseTitleList(courseList));

        return result1 + "\n \n" + result2;
    }

    @GetMapping("/create")
    public String create(@RequestParam String studentName, @RequestParam List<String> courseTitleList) {
        Set<Course> courseSet = courseTitleList.stream().map(Course::new).collect(Collectors.toSet());
        courseRepository.saveAll(courseSet);
        Student student = new Student(studentName, courseSet);
        Student save = studentRepository.save(student);
        return jsonUtils.convertToJson(new StudentDto(save));
    }

    public List<String> getCourseTitleList(List<Course> courseList) {
        return courseList.stream().map(Course::getTitle).collect(Collectors.toList());
    }

}
