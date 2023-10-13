package com.example.ordermanagementrestapi.controller;

import com.example.ordermanagementrestapi.dto.LoginDto;
import com.example.ordermanagementrestapi.dto.UserRegisterDto;
import com.example.ordermanagementrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userRegistration(@RequestBody UserRegisterDto userRegisterDto){
        log.info("User sign up request received. Body: "+userRegisterDto.toString());
        String status=userService.register(userRegisterDto);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping(path = "/signin",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userLogin(@RequestBody LoginDto loginDto){
        log.info("Login request received. username:"+loginDto.getUsername());
        String jwt=userService.login(loginDto);
        return new ResponseEntity<>(jwt,HttpStatus.OK);

    }

    @GetMapping(path = "/getUser",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> getUser(){
        return new ResponseEntity<>("Normal User",HttpStatus.OK);
    }
}
