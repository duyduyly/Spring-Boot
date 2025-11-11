package com.webhook.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webhook.model.entity.User;
import com.webhook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @PostMapping("/create")
    public String createUser() throws JsonProcessingException {
        User user = userService.createUser("testuser", "password123");
        User user2 = userService.createUser("testuser02", "password123");
        List<User> users = List.of(user, user2);
        return objectMapper.writeValueAsString(users);
    }
}
