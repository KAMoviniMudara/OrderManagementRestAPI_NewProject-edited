package com.example.ordermanagementrestapi.service.impl;

import com.example.ordermanagementrestapi.config.JwtService;
import com.example.ordermanagementrestapi.dto.LoginDto;
import com.example.ordermanagementrestapi.dto.UserRegisterDto;
import com.example.ordermanagementrestapi.entity.Role;
import com.example.ordermanagementrestapi.entity.User; // Import User class
import com.example.ordermanagementrestapi.repo.RoleRepo;
import com.example.ordermanagementrestapi.repo.UserRepo;
import com.example.ordermanagementrestapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service; // Import Service annotation

import java.util.HashSet;
import java.util.Set;

@Service // Add the Service annotation
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final RoleRepo roleRepo;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, JwtService jwtService, RoleRepo roleRepo,
                           AuthenticationManager authenticationManager, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.roleRepo = roleRepo;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(UserRegisterDto userRegisterDto) {
        try {
            User user = modelMapper.map(userRegisterDto, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = roleRepo.findByRoleName("user");
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(role);
            user.setRoles(userRoles);
            userRepo.save(user);
            return "Success";
        } catch (Exception exception) {
            return "Failed";
        }
    }

    @Override
    public String login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtService.generateToken(userDetails);
        } catch (Exception e) {
            return "Failed";
        }
    }
}
