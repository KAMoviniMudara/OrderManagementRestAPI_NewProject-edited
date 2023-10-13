package com.example.ordermanagementrestapi.repo;

import com.example.ordermanagementrestapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByRoleName(String user);
}
