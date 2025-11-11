package com.webhook.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum OrderType {
    CREATED("order_created"),
    UPDATED("order_updated"),
    DELETE("order_deleted");

    private String type;
}
