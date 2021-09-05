package com.example.shopdemo.controller;

import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import com.example.shopdemo.model.Order;
import com.example.shopdemo.response.ResponseHandler;
import com.example.shopdemo.service.CustomerService;
import com.example.shopdemo.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final OrderService orderService;

    private final CustomerService customerService;

    public CustomerController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    // add mapping for POST /placeOrder/{customerId} - place a new order
    @PostMapping("/placeOrder")
    public ResponseEntity<Object> placeOrder(@RequestBody Order theOrder){

        try {
            /*Customer customer = customerService.findById(customerId);
            System.out.println("customer  - "+customer);
            theOrder.setCustomerId(customerId);*/

            System.out.println("order  - "+theOrder);

            Order result = customerService.saveOrder(theOrder);
            return ResponseHandler.generateResponse("Successfully updated data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

}
