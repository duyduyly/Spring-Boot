package com.alan.security.model.mapper;

import com.alan.security.model.dto.ProfileDTO;
import com.alan.security.model.entity.Profile;
import com.alan.security.model.entity.User;
import com.alan.security.model.request.SignupRequest;

public class ProfileMapper {

    public static ProfileDTO toDTO(Profile profile, User user) {
       return ProfileDTO.builder()
                .id(profile.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .address(profile.getAddress())
                .lastName(profile.getFirstName())
                .firstName(profile.getLastName())
                .old(profile.getOld())
                .build();
    }

    public static Profile mapWithSignupRequest(SignupRequest signUpRequest, User user) {
        return Profile.builder()
                .user(user)
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .build();
    }
}
