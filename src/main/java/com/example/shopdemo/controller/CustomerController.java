package com.example.shopdemo.controller;

import com.example.shopdemo.model.Customer;
import com.example.shopdemo.model.Order;
import com.example.shopdemo.service.CashierService;
import com.example.shopdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    private final CashierService cashierService;

    public CustomerController(CustomerService customerService, CashierService cashierService) {
        this.customerService = customerService;
        this.cashierService = cashierService;
    }

    @PostMapping("/placeOrder/{customerId}")
    public Order placeOrder(@RequestBody Order theOrder,@PathVariable int customerId){

        Customer customer = cashierService.findById(customerId);
        System.out.println("customer  - "+customer);
        theOrder.setCustomerId(customerId);
        System.out.println("order  - "+theOrder);
        customerService.save(theOrder);

        return theOrder;

    }

}
