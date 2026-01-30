package com.example.initialize.initialize_java_8_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "demo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DemoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_ext", length = 10)
    private String fileExt;

    @Column(name = "file_name", length = 128)
    private String fileName;

    @Column(name = "entity_id", length = 50)
    private String entityId;

    @Column(name = "source", length = 128)
    private String source;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    // getters & setters
}

