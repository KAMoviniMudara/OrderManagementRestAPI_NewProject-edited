package com.example.ordermanagementrestapi.controller;

import com.example.ordermanagementrestapi.dto.request.RequestOrderSaveDTO;
import com.example.ordermanagementrestapi.service.OrderService;
import com.example.ordermanagementrestapi.util.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
@CrossOrigin
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> saveOrder(@RequestBody RequestOrderSaveDTO requestOrderSaveDTO) {
        try {
            String id = orderService.addOrder(requestOrderSaveDTO);
            logger.info("Order saved with ID: {}", id);

            return new ResponseEntity<>(new StandardResponse(201, "Order saved", id), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error saving the order", e);
            return new ResponseEntity<>(new StandardResponse(500, "Error saving the order", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
