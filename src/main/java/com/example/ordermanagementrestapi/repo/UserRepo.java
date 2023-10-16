package com.example.ordermanagementrestapi.repo;

import com.example.ordermanagementrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findOneByEmailAndPassword(String email , String password);

    User findByEmail(String email);
}
