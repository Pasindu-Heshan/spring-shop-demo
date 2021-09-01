package com.example.shopdemo.controller;

import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import com.example.shopdemo.service.CashierService;
import com.example.shopdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    public static final String SUCCESS = "success";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    private CashierService cashierService;
    private final CustomerService customerService;

    @Autowired
    public AdminController(CashierService cashierService, CustomerService customerService) {
        this.cashierService = cashierService;
        this.customerService = customerService;
    }

    @Secured({ROLE_ADMIN})
    @GetMapping("/cashiers")
    public List<Cashier> findAll(){
        return cashierService.findAll();
    }

    @Secured({ROLE_ADMIN})
    @GetMapping("/cashiers/activeCashiers/{pageNo}")
    public List<Cashier> findAllActive(@PathVariable int pageNo){
        int pageSize = 2;
        Pageable paging = PageRequest.of(pageNo,pageSize);
        return cashierService.findAllActive(paging);
    }

    @Secured({ROLE_ADMIN})
    @GetMapping("/cashiers/{cashierId}")
    public Cashier getCashier(@PathVariable int cashierId){
        Cashier theCashier = cashierService.findById(cashierId);

        if (theCashier == null){
            throw new RuntimeException("Cashier id not found - " + cashierId);
        }

        return theCashier;
    }

    @Secured({ROLE_ADMIN})
    @PostMapping("/cashiers/addCashier")
    public Cashier addCashier(@RequestBody Cashier theCashier){

        theCashier.setId(0);

        cashierService.save(theCashier);

        return theCashier;

    }

    @Secured({ROLE_ADMIN})
    @PutMapping("/cashiers/updateCashier")
    public Cashier updateCashier(@RequestBody Cashier theCashier){

        cashierService.save(theCashier);

        return theCashier;
    }

    @Secured({ROLE_ADMIN})
    @PutMapping("/cashiers/tempDeleteCashier/{cashierId}")
    public Cashier tempDeleteCashier(@PathVariable int cashierId){

        Cashier theCashier = cashierService.findById(cashierId);

        if (theCashier == null){
            throw new RuntimeException("Cashier id not found - " + cashierId);
        }

        theCashier.setStatus(0);

        cashierService.save(theCashier);

        return theCashier;
    }

    @Secured({ROLE_ADMIN})
    @DeleteMapping("/cashiers/deleteCashier/{cashierId}")
    public String deleteCashier(@PathVariable int cashierId){

        Cashier tempCashier = cashierService.findById(cashierId);

        // throw exception if null
        if (tempCashier == null){
            throw new RuntimeException("Cashier id not found - "+cashierId);
        }

        cashierService.deleteById(cashierId);

        return "Deleted Cashier id - " + cashierId;
    }

    @Secured({ROLE_ADMIN})
    @GetMapping("/activeCustomers/{pageNo}")
    public List<Customer> findAllActiveCustomers(@PathVariable int pageNo){
        int pageSize = 2;
        Pageable paging = PageRequest.of(pageNo,pageSize);
        return customerService.findAllActive(paging);
    }

    @Secured({ROLE_ADMIN})
    @PutMapping("/updateCustomer")
    public Customer updateCustomer(@RequestBody Customer theCustomer){

        customerService.save(theCustomer);

        return theCustomer;
    }

}