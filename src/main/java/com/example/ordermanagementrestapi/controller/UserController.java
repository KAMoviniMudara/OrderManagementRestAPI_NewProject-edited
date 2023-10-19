package com.example.ordermanagementrestapi.controller;

import com.example.ordermanagementrestapi.Response.LoginResponse;
import com.example.ordermanagementrestapi.dto.LoginDto;
import com.example.ordermanagementrestapi.dto.UserDto;
import com.example.ordermanagementrestapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping(path = "/save")
    public String saveUser(@RequestBody UserDto userDto) {
        String id = userService.addUser(userDto);

        // Log a message when a user is saved
        logger.info("User saved with ID: {}", id);

        return id;
    }

    @PostMapping(path = "login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        LoginResponse loginResponse = userService.loginUser(loginDto);

        // Log a message when a user logs in
        logger.info("User logged in with email: {}", loginDto.getEmail());

        return ResponseEntity.ok(loginResponse);
    }
}
