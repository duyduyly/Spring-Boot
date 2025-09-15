package com.docker.orderservice.service.impl;

import com.docker.orderservice.service.OrderService;
import com.docker.orderservice.service.UserClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserClientService userClientService;

    @Override
    public Map<String, Object> getOrder(String id) {
        Map<String, Object> user = userClientService.get(id);

        return Map.of(
                "orderId", "order-" + id,
                "product", "Laptop",
                "user", user
        );
    }
}
