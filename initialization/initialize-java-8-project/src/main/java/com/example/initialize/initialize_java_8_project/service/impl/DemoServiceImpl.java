package com.example.initialize.initialize_java_8_project.service.impl;

import com.example.initialize.initialize_java_8_project.model.entity.DemoEntity;
import com.example.initialize.initialize_java_8_project.repository.DemoRepository;
import com.example.initialize.initialize_java_8_project.service.DemoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DemoServiceImpl implements DemoService {

    private final DemoRepository repository;

    public DemoServiceImpl(DemoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DemoEntity> getAll() {
        return repository.findAllByDeletedFalse();
    }

    @Override
    public DemoEntity getById(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Demo not found: " + id));
    }

    @Override
    public DemoEntity create(DemoEntity demo) {
        demo.setId(null);
        demo.setDeleted(false);
        demo.setCreatedBy("system");
        demo.setUpdatedBy("system");
        return repository.save(demo);
    }

    @Override
    public DemoEntity update(Long id, DemoEntity demo) {
        DemoEntity existing = getById(id);

        existing.setFilePath(demo.getFilePath());
        existing.setFileExt(demo.getFileExt());
        existing.setFileName(demo.getFileName());
        existing.setEntityId(demo.getEntityId());
        existing.setSource(demo.getSource());
        existing.setUpdatedBy("system");

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        DemoEntity existing = getById(id);
        existing.setDeleted(true);
        existing.setUpdatedBy("system");
        repository.save(existing);
    }
}

