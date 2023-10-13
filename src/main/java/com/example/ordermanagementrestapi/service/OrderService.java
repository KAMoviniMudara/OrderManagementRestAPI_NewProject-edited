package com.example.ordermanagementrestapi.service;

import com.example.ordermanagementrestapi.dto.request.RequestOrderSaveDTO;

public interface OrderService {
    String addOrder(RequestOrderSaveDTO requestOrderSaveDTO);
}
