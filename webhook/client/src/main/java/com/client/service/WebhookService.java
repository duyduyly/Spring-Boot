package com.client.service;

import com.client.model.OrderEvent;
import com.client.model.entity.ClientSystemLog;
import com.client.repository.SystemLogRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WebhookService {

    @Value("${order.webhook.secret-key}")
    private String secretKey;

    private final SystemLogRepository systemLogRepository;
    private final Gson gson;

    public ResponseEntity<?> retrieveWebhook(OrderEvent orderEvent, String secret) {
        String payload = gson.toJson(orderEvent);
        try {
            String calculated = this.signPayload(payload, secretKey);
            if (!Objects.equals(calculated, secret)) {
                this.log(HttpStatus.UNAUTHORIZED.getReasonPhrase(), payload);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            this.log("Ok", payload);
            return ResponseEntity.ok(new ArrayList<>());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            this.log(HttpStatus.BAD_REQUEST.getReasonPhrase(), payload);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


    private String signPayload(String payload, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        byte[] hash = sha256_HMAC.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    private void log(String type, String request) {
        ClientSystemLog clientSystemLog = ClientSystemLog.builder()
                .domain("oder_webhook")
                .type(type)
                .url("")
                .request(request)
                .response("")
                .build();

        systemLogRepository.save(clientSystemLog);
    }

}
