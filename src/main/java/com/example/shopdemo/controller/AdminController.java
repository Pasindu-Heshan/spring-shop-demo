package com.example.shopdemo.controller;

import com.example.shopdemo.model.Admin;
import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import com.example.shopdemo.response.ResponseHandler;
import com.example.shopdemo.service.AdminService;
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
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    private CashierService cashierService;

    private final CustomerService customerService;

    @Autowired
    public AdminController(CashierService cashierService, CustomerService customerService, AdminService adminService) {
        this.cashierService = cashierService;
        this.customerService = customerService;
        this.adminService = adminService;
    }

    // add mapping for GET /admins
    @GetMapping(value = "/admins")
    public ResponseEntity<Object> findAllAdmins() {
        try {
            List<Admin> result = adminService.findAll();
            return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    // expose "/cashiers" and return list of all cashiers
    @GetMapping("/cashiers")
    public ResponseEntity<Object> findAll(){
        try {
            List<Cashier> result = cashierService.findAll();
            return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    // add mapping for GET /cashiers/activeCashiers -get all active cashiers with paging
    @GetMapping("/activeCashiers")
    public ResponseEntity<Map<String, Object>> findAllActive(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size){
        try{
            List<Cashier> cashiers = new ArrayList<Cashier>();
            Pageable paging = PageRequest.of(page,size);
            Page<Cashier> pageCashier;
            pageCashier= cashierService.findAllActive(paging);
            cashiers = pageCashier.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("activeCashiers", cashiers);
            response.put("currentPage", pageCashier.getNumber());
            response.put("totalItems", pageCashier.getTotalElements());
            response.put("totalPages", pageCashier.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // add mapping for GET /cashiers/{cashierId}
    @GetMapping("/cashiers/{cashierId}")
    public ResponseEntity<Object> getCashier(@PathVariable int cashierId){
        try {
            Cashier result = cashierService.findById(cashierId);
            return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    // add mapping for POST /cashiers/addCashier - add new cashier
    @PostMapping("/cashiers/addCashier")
    public ResponseEntity<Object> addCashier(@RequestBody Cashier theCashier){

        try {
            theCashier.setId(0);
            Cashier result = adminService.saveCashier(theCashier);
            return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    // add mapping for PUT /cashiers/updateCashier - update existing cashier
    @PutMapping("/cashiers/updateCashier/{cashierId}")
    public ResponseEntity<Object> updateCashier(@RequestBody Cashier theCashier,@PathVariable int cashierId){

        try {
            Cashier cashier = cashierService.findById(cashierId);

            cashier.setName(theCashier.getName());
            cashier.setUsername(theCashier.getUsername());
            cashier.setPassword(theCashier.getPassword());

            Cashier result = adminService.updateCashier(cashier);
            return ResponseHandler.generateResponse("Successfully updated data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    // add mapping for PUT /cashiers/tempDeleteCashier/{cashierId} - temporary delete cashier
    @PutMapping("/cashiers/tempDeleteCashier/{cashierId}")
    public ResponseEntity<Object> tempDeleteCashier(@PathVariable int cashierId){

        try {
            Cashier theCashier = cashierService.findById(cashierId);
            theCashier.setStatus(0);
            Cashier result = adminService.save(theCashier);
            return ResponseHandler.generateResponse("Successfully disabled cashier!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    // add mapping for DELETE /cashiers/deleteCashier/{cashierId} - delete existing cashier
    @DeleteMapping("/cashiers/deleteCashier/{cashierId}")
    public ResponseEntity<Object> deleteCashier(@PathVariable int cashierId){

        try {
            Cashier tempCashier = cashierService.findById(cashierId);
            Cashier result = cashierService.deleteById(cashierId);
            return ResponseHandler.generateResponse("Successfully deleted cashier!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    // add mapping for GET /activeCustomers get all active customers with paging
    @GetMapping("/activeCustomers")
    public ResponseEntity<Map<String, Object>> findAllActiveCustomers(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size){

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

    // add mapping for PUT /updateCustomer - update existing customer
    @PutMapping("/updateCustomer/{customerId}")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customer theCustomer,@PathVariable int customerId){

        try {
            Customer customer = customerService.findById(customerId);

            customer.setName(theCustomer.getName());
            customer.setUsername(theCustomer.getUsername());
            customer.setPassword(theCustomer.getPassword());
            customer.setAddress(theCustomer.getAddress());
            customer.setPhoneNumber(theCustomer.getPhoneNumber());


            Customer result = adminService.updateCustomer(customer);
            return ResponseHandler.generateResponse("Successfully updated data!", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

}