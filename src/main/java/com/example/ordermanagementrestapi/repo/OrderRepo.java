package com.example.ordermanagementrestapi.repo;

import com.example.ordermanagementrestapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Integer> {
}
