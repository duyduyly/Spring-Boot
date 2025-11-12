package com.webhook.model.request;

import lombok.Data;

@Data
public class WebhookRequest {
    private String username;
    private String url;
    private String topicName;
}
