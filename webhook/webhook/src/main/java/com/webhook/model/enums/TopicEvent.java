package com.webhook.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum TopicEvent {
    ORDER_EVENT("order.event");
    private String event;
}
