package com.alan.entity_mapping.mapping.one_to_one.test;

import com.alan.entity_mapping.mapping.one_to_one.Profile;
import com.alan.entity_mapping.mapping.one_to_one.User;
import com.alan.utils.JsonUtils;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/one-to-one")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private Faker faker;

    @Autowired
    private JsonUtils jsonUtils;

    @GetMapping("/get")
    public String get() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = users.stream().map(UserDto::new).toList();
        return jsonUtils.convertToJson(userDtoList);
    }

    @GetMapping("/create")
    public String create(@RequestParam String username) {
        try {
            User user = this.createUser(username);
            return jsonUtils.convertToJson(new UserDto(user));
        } catch (Exception e) {
            return "Username Existed";
        }
    }

    @GetMapping("/update")
    public String update(@RequestParam String username) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow();
            user.setUsername(user.getUsername());
            Profile profile = user.getProfile();
            profile.setFullName(profile.getFullName() + " Updated");
            return jsonUtils.convertToJson(new UserDto(userRepository.save(user)));
        } catch (Exception e) {
            return "Username Do Not Exist";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam String username) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow();
            userRepository.delete(user);
            return "Delete Success, Let go to Api Get to check!";
        } catch (Exception e) {
            return "Username Do Not Exist";
        }
    }

    public User createUser(String username) {
        if(username.isEmpty()) {
            username = faker.name().name();
        }
        Profile profile = new Profile();
        profile.setFullName(faker.name().fullName());
        profile.setPhone(faker.phoneNumber().phoneNumber());

        User user = new User();
        user.setUsername(username);
        user.setProfile(profile);
        return userRepository.save(user);// Profile is also saved automatically
    }
}
