package com.docker.springbootmysql.service;

import com.docker.springbootmysql.model.dto.StudentDto;

import java.util.List;

public interface StudentService {

    void dummyStudentsData();
    List<StudentDto> getAllStudents();
    StudentDto getStudentById(Long id);
}
