package com.example.ordermanagementrestapi.service;

import com.example.ordermanagementrestapi.dto.LoginDto;
import com.example.ordermanagementrestapi.dto.UserRegisterDto;

public interface UserService {
    String register(UserRegisterDto userRegisterDto);
    String login(LoginDto loginDto);
}
