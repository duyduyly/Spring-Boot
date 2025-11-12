package com.webhook.repository;

import com.webhook.model.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    java.util.Optional<Topic> findByName(String name);
}

