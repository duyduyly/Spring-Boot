package com.alan.entity_mapping.mapping.one_to_one.test;

import com.alan.entity_mapping.mapping.one_to_one.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String fullName;
    private String phone;


    public UserDto(User user) {
        this.username = user.getUsername();
        this.fullName = user.getProfile().getFullName();
        this.phone = user.getProfile().getPhone();
    }
}
