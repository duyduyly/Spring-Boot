package com.webhook.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "system_log") // "order" is reserved in SQL
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String domain;

    @Column(length = 50, nullable = false)
    private String type;

    @Column(length = 500, nullable = false)
    private String description;

    @Column(length = 500, nullable = false)
    private String url;

    @Lob
    @Column(name = "request_payload")
    private String request;

    @Lob
    @Column(name = "response_payload")
    private String response;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @PrePersist
    public void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}
