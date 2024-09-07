package com.example.ordermanagementrestapi.controller;

import com.example.ordermanagementrestapi.dto.CustomerDTO;
import com.example.ordermanagementrestapi.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/customer")
@CrossOrigin
public class CustomerController {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/save")
    public String saveCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            customerService.addCustomer(customerDTO);
            logger.info("Customer saved with name: {}", customerDTO.getCustomerName());
            return "Saved";
        } catch (Exception e) {
            logger.error("Error saving customer", e);
            return "Error saving customer";
        }
    }

    @PostMapping(path = "/update-by-name")
    public String updateCustomerByName(@RequestBody CustomerDTO customerDTO) {
        try {
            String updated = customerService.updateCustomerByName(customerDTO);
            if (updated != null) {
                logger.info("Customer updated by name: {}", customerDTO.getCustomerName());
                return "Updated";
            } else {
                logger.error("Failed to update customer by name: {}", customerDTO.getCustomerName());
                return "Update Failed";
            }
        } catch (Exception e) {
            logger.error("Error updating customer by name", e);
            return "Error updating customer by name";
        }
    }

    @GetMapping(path = "/get-by-id", params = "id")
    public CustomerDTO getCustomerById(@RequestParam(value = "id") int customerId) {
        try {
            CustomerDTO customerDTO = customerService.getCustomerByID(customerId);
            logger.info("Retrieved customer by ID: {}", customerId);
            return customerDTO;
        } catch (Exception e) {
            logger.error("Error retrieving customer by ID: {}", customerId, e);
            return null;
        }
    }

    @GetMapping(path = "/get-all-customers")
    public List<CustomerDTO> getAllCustomers() {
        try {
            List<CustomerDTO> allCustomers = customerService.getAllCustomers();
            logger.info("Retrieved all customers");
            return allCustomers;
        } catch (Exception e) {
            logger.error("Error retrieving all customers", e);
            return null;
        }
    }

    @DeleteMapping(path = "/delete-customer/{id}")
    public String deleteCustomer(@PathVariable(value = "id") int customerID) {
        try {
            String deleteCustomer = customerService.deleteCustomer(customerID);
            logger.info("Deleted customer with ID: {}", customerID);
            return deleteCustomer;
        } catch (Exception e) {
            logger.error("Error deleting customer with ID: {}", customerID, e);
            return "Error deleting customer";
        }
    }

    @PatchMapping(path = "/deactivate-customer-by-name")
    public String deactivateCustomerByName(@RequestBody Map<String, String> requestBody) {
        try {
            String customerName = requestBody.get("customerName");
            String deactivateStatus = customerService.deactivateCustomerByName(customerName);
            logger.info("Deactivated customer by name: {}", customerName);
            return deactivateStatus;
        } catch (Exception e) {
            logger.error("Error deactivating customer by name", e);
            return "Error deactivating customer";
        }
    }

    @PatchMapping(path = "/activate-customer-by-name")
    public String activateItemByName(@RequestBody Map<String, String> requestBody) {
        try {
            String customerName = requestBody.get("customerName");
            String activateStatus = customerService.activateCustomerByName(customerName);
            logger.info("Activated customer by name: {}", customerName);
            return activateStatus;
        } catch (Exception e) {
            logger.error("Error activating customer by name", e);
            return "Error activating customer";
        }
    }
}
