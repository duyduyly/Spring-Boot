package com.alan.security.controller;


import com.alan.security.constant.Constant;
import com.alan.security.model.response.CommonResponse;
import com.alan.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthService userService;


    @GetMapping()
    public ResponseEntity<?> getProfile(Principal principal) throws NoSuchFieldException {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .message(Constant.SUCCESS)
                .data(userService.getProfile(principal.getName()))
                .build(), HttpStatus.OK);
    }
}
