package com.webhook.service;

import com.webhook.model.entity.User;
import com.webhook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // In a real application, ensure to hash the password
        return userRepository.save(user);
    }
}
