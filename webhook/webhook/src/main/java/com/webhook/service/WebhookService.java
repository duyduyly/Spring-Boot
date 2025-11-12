package com.webhook.service;

import com.google.gson.Gson;
import com.webhook.model.OrderEvent;
import com.webhook.model.entity.SystemLog;
import com.webhook.model.entity.Topic;
import com.webhook.model.entity.User;
import com.webhook.model.entity.Webhook;
import com.webhook.model.request.WebhookRequest;
import com.webhook.repository.SystemLogRepository;
import com.webhook.repository.TopicRepository;
import com.webhook.repository.UserRepository;
import com.webhook.repository.WebhookRepository;
import com.webhook.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final WebhookRepository webhookRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final CommonUtils commonUtils;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final SystemLogRepository systemLogRepository;



    public Webhook registryWebhook(WebhookRequest request) {
        Topic topic = topicRepository.findByName(request.getTopicName())
                .orElseThrow(() -> new IllegalArgumentException("Topic not found: " + request.getTopicName()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUsername()));

        Webhook webhook = new Webhook();
        webhook.setUrl(request.getUrl());
        webhook.setTopic(topic);
        webhook.setUser(user);
        webhook.setSecretKey(commonUtils.generateSignatureSecret());
        webhook.setActive(true);

        return webhookRepository.save(webhook);
    }

    @Retryable(
            value = {RuntimeException.class, HttpServerErrorException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void outboundOrderEventWebhook(OrderEvent event) {
        try {
            Optional<Webhook> byUserUsername = webhookRepository.findByUser_UsernameAndTopic_Name(event.getUsername(), event.getTopicName());
            AtomicReference<String> response = new AtomicReference<>("");
            AtomicReference<String> type = new AtomicReference<>("INFO");
            byUserUsername.ifPresent(hook -> {
                try {
                    String payload = gson.toJson(event);
                    String signature = signPayload(payload, hook.getSecretKey());
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.set("X-Signature", signature);

                    HttpEntity<String> entity = new HttpEntity<>(payload, headers);
                    ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(hook.getUrl(), entity, String.class);
                    response.set(stringResponseEntity.getBody());
                } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                    type.set("ERROR");
                    throw new RuntimeException("Failed to sign payload for webhook: " + hook.getUrl(), e);
                }finally {
                    SystemLog systemLog = SystemLog.builder()
                            .domain("WEBHOOK_SERVICE")
                            .description("Dispatched webhook for event: " + event)
                            .url(hook.getUrl())
                            .request(gson.toJson(event))
                            .response(response.get())
                            .type(type.get())
                            .build();
                    systemLogRepository.save(systemLog);
                }

            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to send webhook for event: " + event, e);
        }
    }


    private String signPayload(String payload, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        byte[] hash = sha256_HMAC.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

}
