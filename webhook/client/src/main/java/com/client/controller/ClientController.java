package com.client.controller;

import com.client.model.OrderEvent;
import com.client.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RequestMapping("/orders")
@RestController
@RequiredArgsConstructor
public class ClientController {

    private final WebhookService webhookService;

    @PostMapping("webhook")
    public ResponseEntity<?> webhook(@RequestBody OrderEvent orderEvent, @RequestHeader("X-Signature") String secretKey){
        return webhookService.retrieveWebhook(orderEvent, secretKey);
    }
}
