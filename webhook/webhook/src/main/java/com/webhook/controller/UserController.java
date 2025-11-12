package com.webhook.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webhook.model.entity.User;
import com.webhook.model.request.UserRequest;
import com.webhook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String createUser(@RequestBody UserRequest request) throws JsonProcessingException {
        User user = userService.createUser(request.getUsername(), request.getPassword());
        return objectMapper.writeValueAsString(user);
    }
}
