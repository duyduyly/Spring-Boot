package com.docker.orderservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(
        name = "userService",
        url = "${internal.api}"
)
public interface UserClientService {

    @GetMapping("/{id}")
    public Map<String, Object> get(@PathVariable("id") String id);
}
