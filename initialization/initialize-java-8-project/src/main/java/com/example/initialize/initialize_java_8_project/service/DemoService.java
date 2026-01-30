package com.example.initialize.initialize_java_8_project.service;

import com.example.initialize.initialize_java_8_project.model.entity.DemoEntity;

import java.util.List;

public interface DemoService {

    List<DemoEntity> getAll();

    DemoEntity getById(Long id);

    DemoEntity create(DemoEntity demo);

    DemoEntity update(Long id, DemoEntity demo);

    void delete(Long id);
}
