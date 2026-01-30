package com.docker.springbootmysql.service.impl;

import com.docker.springbootmysql.model.dto.StudentDto;
import com.docker.springbootmysql.model.entity.Student;
import com.docker.springbootmysql.repository.StudentRepository;
import com.docker.springbootmysql.service.StudentService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final Faker faker;

    @Override
    public void dummyStudentsData() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String name = faker.name().fullName();
            students.add(new Student(name));
            log.info("Generated student name: {}", name);
        }
        studentRepository.saveAll(students);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> new StudentDto(student.getId(), student.getName()))
                .toList();
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (Objects.isNull(student)) {
            return null;
        }
        return new StudentDto(student.getId(), student.getName());
    }
}
