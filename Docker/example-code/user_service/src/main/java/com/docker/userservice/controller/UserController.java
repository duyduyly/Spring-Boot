package com.docker.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public Map<String, String> get(@PathVariable String id) {
        return java.util.Map.of(
                "id", id,
                "name", "User-" + id
        );
    }
}


