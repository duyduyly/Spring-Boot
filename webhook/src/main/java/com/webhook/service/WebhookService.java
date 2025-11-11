package com.webhook.service;

import com.webhook.model.OrderEvent;
import com.webhook.model.entity.Topic;
import com.webhook.model.entity.User;
import com.webhook.model.entity.Webhook;
import com.webhook.model.request.WebhookRequest;
import com.webhook.repository.TopicRepository;
import com.webhook.repository.UserRepository;
import com.webhook.repository.WebhookRepository;
import com.webhook.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final WebhookRepository webhookRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final CommonUtils commonUtils;
    private final RestTemplate restTemplate;


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

    public void outboundOrderEventWebhook(OrderEvent event) {
        Optional<Webhook> byUserUsername = webhookRepository.findByUser_UsernameAndTopic_Name(event.getUsername(), event.getTopicName());

        byUserUsername.ifPresent(webhook -> {
            // send webhook
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<OrderEvent> entity = new HttpEntity<>(event, headers);
            restTemplate.postForEntity(webhook.getUrl(), entity, String.class);
        });



    }

}
