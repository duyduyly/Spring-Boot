package com.webhook.webhook;

import com.webhook.model.OrderEvent;
import com.webhook.repository.SystemLogRepository;
import com.webhook.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WebhookConsumer {
    private final WebhookService webhookService;

    @KafkaListener(topics = "order-events", groupId = "webhook-group")
    public void consumeEvent(OrderEvent event) {
        webhookService.outboundOrderEventWebhook(event);
    }
}
