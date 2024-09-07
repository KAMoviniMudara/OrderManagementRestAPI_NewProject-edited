package com.example.ordermanagementrestapi.service;

import com.example.ordermanagementrestapi.Response.LoginResponse;
import com.example.ordermanagementrestapi.dto.LoginDto;
import com.example.ordermanagementrestapi.dto.UserDto;

public interface UserService {

    String addUser(UserDto userDto);
    LoginResponse loginUser(LoginDto loginDto);

    UserDto getUserByEmail(String userEmail);
}
