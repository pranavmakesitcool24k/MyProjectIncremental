package com.edutech.progressive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edutech.progressive.entity.User;
import com.edutech.progressive.service.impl.UserLoginServiceImpl;

@RestController
@RequestMapping("/user")
public class UserLoginController {

    private final UserLoginServiceImpl userLoginService;

    @Autowired
    public UserLoginController(UserLoginServiceImpl userLoginService) {
        this.userLoginService = userLoginService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userLoginService.createUser(user);
            return ResponseEntity.ok(savedUser);  
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
}