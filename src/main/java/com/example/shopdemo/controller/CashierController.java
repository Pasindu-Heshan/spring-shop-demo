package com.example.shopdemo.controller;

import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import com.example.shopdemo.response.ResponseHandler;
import com.example.shopdemo.service.CashierService;
import com.example.shopdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cashier")
public class CashierController {

    private CustomerService customerService;
    private CashierService cashierService;

    @Autowired
    public CashierController(CustomerService customerService, CashierService cashierService){
        this.customerService = customerService;
        this.cashierService = cashierService;
    }

    // expose "/customers" and return list of all customers
    @GetMapping("/customers")
    public List<Customer> findAll(){
        return customerService.findAll();
    }

    // add mapping for POST /customers/addCustomer - add new customer
    @PostMapping("/customers/addCustomer")
    public ResponseEntity<Object> addCustomer(@RequestBody Customer theCustomer){

        try {
            theCustomer.setId(0);
            Customer result = cashierService.saveCustomer(theCustomer);
            return ResponseHandler.generateResponse("Successfully added customer!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    // add mapping for GET /activeCustomers - get all active customers with paging
    @GetMapping("/activeCustomers")
    public ResponseEntity<Map<String, Object>> findAllActiveCustomers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size){

        try{
            List<Customer> customers = new ArrayList<Customer>();
            Pageable paging = PageRequest.of(page,size);
            Page<Customer> pageCustomer;
            pageCustomer = customerService.findAllActive(paging);
            customers = pageCustomer.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("activeCustomers", customers);
            response.put("currentPage", pageCustomer.getNumber());
            response.put("totalItems", pageCustomer.getTotalElements());
            response.put("totalPages", pageCustomer.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // add mapping for PUT /customers/tempDeleteCustomer/{customerId} - temporary delete customer
    @PutMapping("/customers/tempDeleteCustomer/{customerId}")
    public ResponseEntity<Object> tempDeleteCustomer(@PathVariable int customerId){

        try {
            Customer theCustomer = customerService.findById(customerId);
            theCustomer.setStatus(0);
            Customer result = cashierService.save(theCustomer);
            return ResponseHandler.generateResponse("Successfully disabled customer!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

}
