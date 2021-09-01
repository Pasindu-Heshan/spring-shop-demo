package com.example.shopdemo.controller;

import com.example.shopdemo.model.Customer;
import com.example.shopdemo.model.Order;
import com.example.shopdemo.service.CustomerService;
import com.example.shopdemo.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final OrderService orderService;

    private final CustomerService customerService;

    public CustomerController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @PostMapping("/placeOrder/{customerId}")
    public Order placeOrder(@RequestBody Order theOrder,@PathVariable int customerId){

        Customer customer = customerService.findById(customerId);
        System.out.println("customer  - "+customer);
        theOrder.setCustomerId(customerId);
        System.out.println("order  - "+theOrder);
        orderService.save(theOrder);

        return theOrder;

    }

}
