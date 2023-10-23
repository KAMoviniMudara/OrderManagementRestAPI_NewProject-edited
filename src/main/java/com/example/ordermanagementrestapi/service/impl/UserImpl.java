package com.example.ordermanagementrestapi.service.impl;

import com.example.ordermanagementrestapi.Response.LoginResponse;
import com.example.ordermanagementrestapi.dto.LoginDto;
import com.example.ordermanagementrestapi.dto.UserDto;
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
        com.example.ordermanagementrestapi.entity.User user = new com.example.ordermanagementrestapi.entity.User(
                userDto.getUserId(),
                userDto.getUserName(),
                userDto.getEmail(),
                this.passwordEncoder.encode(userDto.getPassword())
        );

        userRepo.save(user);
        logger.info("User added: {}", user.getUserName());
        return user.getUserName();
    }

    @Override
    public LoginResponse loginUser(LoginDto loginDto) {
        com.example.ordermanagementrestapi.entity.User user1 = userRepo.findByEmail(loginDto.getEmail());
        if (user1 != null) {
            String password = loginDto.getPassword();
            String encodedPassword = user1.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<com.example.ordermanagementrestapi.entity.User> user = userRepo.findOneByEmailAndPassword(loginDto.getEmail(), encodedPassword);
                if (user.isPresent()) {
                    logger.info("User logged in: {}", user1.getUserName());
                    return new LoginResponse("Login Success", true);
                } else {
                    logger.warn("Login failed for user: {}", user1.getUserName());
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                logger.warn("Password mismatch for user: {}", user1.getUserName());
                return new LoginResponse("Password Not Match", false);
            }
        } else {
            logger.warn("Email not found: {}", loginDto.getEmail());
            return new LoginResponse("Email not exists", false);
        }
    }
}
