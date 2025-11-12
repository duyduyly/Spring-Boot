package com.client.repository;

import com.client.model.entity.ClientSystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemLogRepository extends JpaRepository<ClientSystemLog, Long> {
}
