package com.example.ordermanagementrestapi.controller;

import com.example.ordermanagementrestapi.dto.CustomerDTO;
import com.example.ordermanagementrestapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/save")
    public String saveCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.addCustomer(customerDTO);
        return "Saved";
    }

    @PostMapping(path = "/update-by-name")
    public String updateCustomerByName(@RequestBody CustomerDTO customerDTO) {
        String updated = customerService.updateCustomerByName(customerDTO);
        if (updated != null) {
            return "Updated";
        } else {
            return "Update Failed";
        }
    }

    @GetMapping(path = "/get-by-id", params = "id")
    public CustomerDTO getCustomerById(@RequestParam(value = "id") int customerId) {
        CustomerDTO customerDTO = customerService.getCustomerByID(customerId);
        return customerDTO;
    }

    @GetMapping(path = "/get-all-customers")
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> allCustomers = customerService.getAllCustomers();
        return allCustomers;
    }

    @DeleteMapping(path = "/delete-customer/{id}")
    public String deleteCustomer(@PathVariable(value = "id") int customerID) {
        String deleteCustomer = customerService.deleteCustomer(customerID);
        return deleteCustomer;
    }

    @PatchMapping(path = "/deactivate-customer-by-name")
    public String deactivateCustomerByName(@RequestBody Map<String, String> requestBody) {
        String customerName = requestBody.get("customerName");

        String deactivateStatus = customerService.deactivateCustomerByName(customerName);
        return deactivateStatus;
    }

    @PatchMapping(path = "/activate-customer-by-name")
    public String activateItemByName(@RequestBody Map<String, String> requestBody) {
        String customerName = requestBody.get("customerName");

        String activateStatus = customerService.activateCustomerByName(customerName);
        return activateStatus;
    }



}
