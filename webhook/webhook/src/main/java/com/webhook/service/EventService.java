package com.webhook.service;

import com.webhook.model.OrderEvent;
import com.webhook.model.enums.TopicEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishEvent(OrderEvent event, TopicEvent topicEvent) {
        kafkaTemplate.send(topicEvent.getEvent(), event);
        System.out.println("📤 Published event to Kafka: " + event);
    }
}
