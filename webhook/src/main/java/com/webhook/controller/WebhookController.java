package com.webhook.controller;

import com.webhook.model.entity.Webhook;
import com.webhook.model.request.WebhookRequest;
import com.webhook.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;


    @PostMapping("/register")
    public ResponseEntity<Webhook> registerWebhook(@RequestBody WebhookRequest request) {
        Webhook webhook = webhookService.registryWebhook(request);
        return ResponseEntity.ok(webhook);
    }

}
