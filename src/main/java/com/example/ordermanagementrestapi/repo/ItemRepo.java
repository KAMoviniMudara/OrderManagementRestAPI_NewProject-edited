package com.example.ordermanagementrestapi.repo;

import com.example.ordermanagementrestapi.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface ItemRepo extends JpaRepository <Item, Integer>{
   
    List<Item>findAllByActiveStateIs(Boolean b);
    Page<Item> findAllByActiveStateEquals(boolean activeState, Pageable pageable);
    int countAllByActiveState(boolean activeState);

    Item findByItemName(String itemName);

    List<Item> findAllByItemName(String itemName);
}
