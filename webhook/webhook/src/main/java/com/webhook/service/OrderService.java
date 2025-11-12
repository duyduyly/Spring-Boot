package com.webhook.service;

import com.webhook.model.OrderEvent;
import com.webhook.model.entity.Order;
import com.webhook.model.entity.User;
import com.webhook.model.enums.OrderType;
import com.webhook.model.enums.TopicEvent;
import com.webhook.repository.OrderRepository;
import com.webhook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final EventService producer;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order create(Order order, String username) {
        User user = userRepository.findByUsername(username)
                .orElse(null);

        Order saved = orderRepository.save(order);
        saved.setUser(user);
        sendEvent(OrderType.CREATED, saved, username);
        return saved;
    }

    public Order update(Long id, Order updated) {
        Order existing = getById(id);
        existing.setProduct(updated.getProduct());
        existing.setQuantity(updated.getQuantity());
        existing.setPrice(updated.getPrice());
        existing.setStatus(updated.getStatus());
        Order saved = orderRepository.save(existing);
        sendEvent(OrderType.UPDATED, saved, saved.getUser().getUsername());
        return saved;
    }

    public void delete(Long id) {
        Order existing = getById(id);
        existing.setIsDeleted(true);
        orderRepository.save(existing);

        sendEvent(OrderType.DELETE, existing, existing.getUser().getUsername());
    }

    private void sendEvent(OrderType eventType, Order order, String username) {
        OrderEvent message = OrderEvent.builder()
                .eventType(eventType.name())
                .orderId(order.getId().toString())
                .status(order.getStatus())
                .timestamp(System.currentTimeMillis())
                .username(username)
                .build();
        producer.publishEvent(message,  TopicEvent.ORDER_EVENT);
    }

}
