package com.webhook.repository;

import com.webhook.model.entity.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebhookRepository extends JpaRepository<Webhook, Long> {
    Optional<Webhook> findByUser_UsernameAndTopic_Name(String username, String topicName);
}
