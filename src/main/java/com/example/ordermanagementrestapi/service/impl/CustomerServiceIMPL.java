package com.example.ordermanagementrestapi.service.impl;

import com.example.ordermanagementrestapi.dto.CustomerDTO;
import com.example.ordermanagementrestapi.entity.Customer;
import com.example.ordermanagementrestapi.repo.CustomerRepo;
import com.example.ordermanagementrestapi.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceIMPL implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceIMPL.class);
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addCustomer(CustomerDTO customerDTO) {
        logger.info("Adding a customer: {}", customerDTO.getCustomerName());

        Customer customer = new Customer(
                customerDTO.getCustomerID(),
                customerDTO.getCustomerName(),
                customerDTO.getCustomerAddress(),
                customerDTO.getSalary(),
                customerDTO.getContactNumbers(),
                customerDTO.getNic(),
                customerDTO.isActiveState()
        );

        if (!customerRepo.existsById(customer.getCustomerID())) {
            customerRepo.save(customer);
        } else {
            logger.warn("Customer already exists: {}", customer.getCustomerName());
        }
    }

    @Override
    public String updateCustomer(CustomerDTO customerDTO) {
        if (customerRepo.existsById(customerDTO.getCustomerID())) {
            logger.info("Updating customer by ID: {}", customerDTO.getCustomerID());

            Customer customer = customerRepo.getById(customerDTO.getCustomerID());
            customer.setCustomerAddress(customerDTO.getCustomerAddress());
            customer.setCustomerName(customerDTO.getCustomerName());
            customer.setSalary(customerDTO.getSalary());

            customerRepo.save(customer);
            return "updated";
        } else {
            logger.warn("No customer found for ID: {}", customerDTO.getCustomerID());
            return "no customer found for that id";
        }
    }

    @Override
    public CustomerDTO getCustomerByID(int customerId) {
        Customer customer = customerRepo.getById(customerId);
        if (customer != null) {
            CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
            return customerDTO;
        } else {
            return null;
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> getCustomers = customerRepo.findAll();
        List<CustomerDTO> customerDTOList = new ArrayList<>();

        if (customerDTOList != null) {
            customerDTOList = modelMapper.map(getCustomers, new TypeToken<List<CustomerDTO>>() {
            }.getType());
        }
        return customerDTOList;
    }

    @Override
    public String deleteCustomer(int customerId) {
        if (customerRepo.existsById(customerId)) {
            customerRepo.deleteById(customerId);
            return "deleted";
        } else {
            return "no customer exists for that id";
        }
    }

    @Override
    public String updateCustomerByName(CustomerDTO customerDTO) {
        try {
            Customer customer = customerRepo.findByCustomerName(customerDTO.getCustomerName());

            if (customer != null) {
                customer.setCustomerAddress(customerDTO.getCustomerAddress());
                customer.setSalary(customerDTO.getSalary());
                customerRepo.save(customer);
                return "Customer Updated by Name";
            } else {
                return "Customer Not Found by Name";
            }
        } catch (Exception e) {
            return "Update by Name Failed";
        }
    }

    @Override
    public String deactivateCustomerByName(String customerName) {
        try {
            Customer customer = customerRepo.findByCustomerName(customerName);
            if (customer == null) {
                return "Customer Not Found";
            }
            customer.setActiveState(false);
            customerRepo.save(customer);
            return "Customer Deactivated";
        } catch (Exception e) {
            return "Deactivation Failed";
        }
    }

    @Override
    public String activateCustomerByName(String customerName) {
        Customer customer = customerRepo.findByCustomerName(customerName);
        if (customer != null) {
            customer.setActiveState(true);
            customerRepo.save(customer);
            return "Customer Activated";
        } else {
            return "Customer Not Found";
        }
    }
}
