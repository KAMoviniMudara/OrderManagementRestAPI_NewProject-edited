package com.example.ordermanagementrestapi.service;

import com.example.ordermanagementrestapi.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    void addCustomer(CustomerDTO customerDTO);
    String updateCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomerByID(int customerId);
    List<CustomerDTO> getAllCustomers();
    String deleteCustomer(int customerID);
    String updateCustomerByName(CustomerDTO customerDTO);
    String deactivateCustomerByName(String customerName);
    String activateCustomerByName(String customerName);

}
