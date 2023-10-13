package com.example.ordermanagementrestapi.repo;

import com.example.ordermanagementrestapi.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails,Integer> {
}
