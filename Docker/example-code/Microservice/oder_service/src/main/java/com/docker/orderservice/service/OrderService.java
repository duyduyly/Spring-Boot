package com.docker.orderservice.service;

import java.util.Map;
import java.util.Objects;

public interface OrderService {
    Map<String, Object> getOrder(String id);
}
