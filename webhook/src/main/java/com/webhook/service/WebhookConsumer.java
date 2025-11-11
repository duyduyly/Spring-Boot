package com.webhook.service;

import com.webhook.model.OrderEvent;
import com.webhook.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class WebhookConsumer {
    private final WebhookService webhookService;

    @KafkaListener(topics = "order-events", groupId = "webhook-group")
    public void consumeEvent(OrderEvent event) {
        System.out.println("📥 Received event: " + event);

        try {
            webhookService.outboundOrderEventWebhook(event);
        } catch (Exception e) {
            System.err.println("❌ Failed to send webhook: " + e.getMessage());
            // 👉 can add retry logic or dead-letter queue here
        }
    }
}
