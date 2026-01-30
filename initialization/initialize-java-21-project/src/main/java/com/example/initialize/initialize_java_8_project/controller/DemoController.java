package com.example.initialize.initialize_java_8_project.controller;

import com.example.initialize.initialize_java_8_project.model.entity.DemoEntity;
import com.example.initialize.initialize_java_8_project.service.DemoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demos")
public class DemoController {

    private final DemoService service;

    public DemoController(DemoService service) {
        this.service = service;
    }

    @GetMapping
    public List<DemoEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DemoEntity getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DemoEntity create(@RequestBody DemoEntity demo) {
        return service.create(demo);
    }

    @PutMapping("/{id}")
    public DemoEntity update(@PathVariable Long id,
                             @RequestBody DemoEntity demo) {
        return service.update(id, demo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

