package com.example.shopdemo.controller;

import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import com.example.shopdemo.service.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cashier")
public class CashierController {

    private CashierService cashierService;

    @Autowired
    public CashierController(CashierService theCashierService){
        cashierService = theCashierService;
    }

    @GetMapping("/customers")
    public List<Customer> findAll(){
        return cashierService.findAll();
    }

    @PostMapping("/customers/addCustomer")
    public Customer addCustomer(@RequestBody Customer theCustomer){

        theCustomer.setId(0);

        cashierService.save(theCustomer);

        return theCustomer;

    }

    @GetMapping("/customers/activeCustomers/{pageNo}")
    public List<Customer> findAllActive(@PathVariable int pageNo){
        int pageSize = 2;
        Pageable paging = PageRequest.of(pageNo,pageSize);
        return cashierService.findAllActive(paging);
    }

    @PutMapping("/customers/tempDeleteCustomer/{customerId}")
    public Customer tempDeleteCustomer(@PathVariable int customerId){

        Customer theCustomer = cashierService.findById(customerId);

        if (theCustomer == null){
            throw new RuntimeException("Customer id not found - " + customerId);
        }

        theCustomer.setStatus(0);

        cashierService.save(theCustomer);

        return theCustomer;
    }

}
