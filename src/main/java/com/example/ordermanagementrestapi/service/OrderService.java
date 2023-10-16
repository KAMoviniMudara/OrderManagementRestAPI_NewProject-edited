package com.example.ordermanagementrestapi.service;

import com.example.ordermanagementrestapi.dto.request.RequestOrderSaveDTO;

import javax.transaction.Transactional;

public interface OrderService {
    String addOrder(RequestOrderSaveDTO requestOrderSaveDTO);

    boolean updateOrderByCustomers(RequestOrderSaveDTO requestOrderSaveDTO);

    @Transactional
    boolean updateOrder(int orderId, RequestOrderSaveDTO requestOrderSaveDTO);
}
