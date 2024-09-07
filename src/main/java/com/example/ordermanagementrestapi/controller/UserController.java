package com.example.ordermanagementrestapi.controller;

import com.example.ordermanagementrestapi.dto.LoginDto;
import com.example.ordermanagementrestapi.dto.UserDto;
import com.example.ordermanagementrestapi.Response.LoginResponse;
import com.example.ordermanagementrestapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        logger.info("Received UserDto email: {}", userDto.getEmail());
        logger.info("Received UserDto role: {}", userDto.getRole());

        String userName = userService.addUser(userDto);
        logger.info("User registered: {}", userName);

        return ResponseEntity.ok("Registration successful for user: " + userName);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginDto loginDto) {
        logger.info("Received login request for email: {}", loginDto.getEmail());

        LoginResponse loginResponse = userService.loginUser(loginDto);
        if (loginResponse.isStatus()) {
            logger.info("User login successful for email: {}", loginDto.getEmail());
        } else {
            logger.warn("User login failed for email: {}", loginDto.getEmail());
        }

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@AuthenticationPrincipal Principal principal) {
        if (principal != null) {
            String userEmail = principal.getName();
            logger.info("Retrieved user profile for email: {}", userEmail);

            UserDto userDto = userService.getUserByEmail(userEmail);
            return ResponseEntity.ok(userDto);
        } else {
            logger.warn("User profile request without authenticated principal");
            return ResponseEntity.badRequest().body(null);
        }
    }
}
