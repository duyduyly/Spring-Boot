package com.webhook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEvent {
    private String eventType; // "order.created" or "order.updated"
    private String orderId;
    private String status;
    private String username;
    private String topicName;
    private Long timestamp;
}
