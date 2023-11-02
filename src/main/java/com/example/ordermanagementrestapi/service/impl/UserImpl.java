package com.example.ordermanagementrestapi.service.impl;

import com.example.ordermanagementrestapi.Response.LoginResponse;
import com.example.ordermanagementrestapi.dto.LoginDto;
import com.example.ordermanagementrestapi.dto.UserDto;
import com.example.ordermanagementrestapi.entity.User;
import com.example.ordermanagementrestapi.repo.UserRepo;
import com.example.ordermanagementrestapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addUser(UserDto userDto) {
        String email = userDto.getEmail();

        User existingUser = userRepo.findByEmail(email);
        if (existingUser != null) {
            logger.warn("User with email '{}' already exists", email);
            return "Email already exists";
        }

        User user = new User(
                userDto.getUserId(),
                userDto.getUserName(),
                userDto.getEmail(),
                this.passwordEncoder.encode(userDto.getPassword()),
                userDto.getRole()
        );

        userRepo.save(user);
        return user.getUserName();
    }

    @Override
    public LoginResponse loginUser(LoginDto loginDto) {
        User user = userRepo.findByEmail(loginDto.getEmail());

        if (user != null) {
            String password = loginDto.getPassword();
            String encodedPassword = user.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);

            if (isPwdRight) {
                if ("ADMIN".equals(user.getRole())) {
                    logger.info("User logged in as ADMIN: {}", user.getUserName());
                    return new LoginResponse("Login Success", true, "ADMIN");
                } else {
                    logger.warn("Login failed for user: {} - Not an admin", user.getUserName());
                    return new LoginResponse("You are not authorized to access", false, "USER");
                }
            } else {
                logger.warn("Password mismatch for user: {}", user.getUserName());
                return new LoginResponse("Incorrect Password", false, null);
            }
        } else {
            logger.warn("Email not found: {}", loginDto.getEmail());
            return new LoginResponse("Email not exists", false, null);
        }
    }

    @Override
    public UserDto getUserByEmail(String userEmail) {
        return null;
    }
}
