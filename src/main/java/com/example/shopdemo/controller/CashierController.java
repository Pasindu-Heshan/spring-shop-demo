package com.example.shopdemo.controller;

import com.example.shopdemo.model.Customer;
import com.example.shopdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cashier")
public class CashierController {

    private CustomerService customerService;

    @Autowired
    public CashierController(CustomerService theCustomerService){
        customerService = theCustomerService;
    }

    @GetMapping("/customers")
    public List<Customer> findAll(){
        return customerService.findAll();
    }

    @PostMapping("/customers/addCustomer")
    public Customer addCustomer(@RequestBody Customer theCustomer){

        theCustomer.setId(0);

        customerService.save(theCustomer);

        return theCustomer;

    }

    @GetMapping("/customers/activeCustomers/{pageNo}")
    public List<Customer> findAllActive(@PathVariable int pageNo){
        int pageSize = 2;
        Pageable paging = PageRequest.of(pageNo,pageSize);
        return customerService.findAllActive(paging);
    }

    @PutMapping("/customers/tempDeleteCustomer/{customerId}")
    public Customer tempDeleteCustomer(@PathVariable int customerId){

        Customer theCustomer = customerService.findById(customerId);

        if (theCustomer == null){
            throw new RuntimeException("Customer id not found - " + customerId);
        }

        theCustomer.setStatus(0);

        customerService.save(theCustomer);

        return theCustomer;
    }

}
