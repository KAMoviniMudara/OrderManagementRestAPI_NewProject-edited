package com.example.ordermanagementrestapi.service.impl;

import com.example.ordermanagementrestapi.dto.request.RequestOrderSaveDTO;
import com.example.ordermanagementrestapi.entity.Order;
import com.example.ordermanagementrestapi.entity.OrderDetails;
import com.example.ordermanagementrestapi.repo.CustomerRepo;
import com.example.ordermanagementrestapi.repo.ItemRepo;
import com.example.ordermanagementrestapi.repo.OrderDetailsRepo;
import com.example.ordermanagementrestapi.repo.OrderRepo;
import com.example.ordermanagementrestapi.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceIMPL implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceIMPL.class);

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private OrderDetailsRepo orderDetailsRepo;

    @Override
    @Transactional
    public String addOrder(RequestOrderSaveDTO requestOrderSaveDTO) {
        try {
            Order order = new Order(
                    requestOrderSaveDTO.getDate(),
                    requestOrderSaveDTO.getTotal(),
                    customerRepo.getById(requestOrderSaveDTO.getCustomers())
            );
            orderRepo.save(order);

            if (orderRepo.existsById(order.getOrderID())) {
                List<OrderDetails> orderDetails = new ArrayList<>();

                orderDetails = modelMapper.map(requestOrderSaveDTO.getOrderDetails(), new TypeToken<List<OrderDetails>>() {
                }.getType());
                for (int i = 0; i < orderDetails.size(); i++) {
                    orderDetails.get(i).setOrders(order);
                    orderDetails.get(i).setItems(itemRepo.getById(requestOrderSaveDTO.getOrderDetails().get(i).getItems()));
                }
                if (orderDetails.size() > 0) {
                    orderDetailsRepo.saveAll(orderDetails);
                }
                return "saved";
            }
            return null;
        } catch (Exception e) {
            logger.error("Error while adding an order: " + e.getMessage(), e);
            return "Error adding order";
        }
    }

    @Override
    public boolean updateOrderByCustomers(RequestOrderSaveDTO requestOrderSaveDTO) {
        return false;
    }

    @Override
    public boolean updateOrder(int orderId, RequestOrderSaveDTO requestOrderSaveDTO) {
        return false;
    }
}
