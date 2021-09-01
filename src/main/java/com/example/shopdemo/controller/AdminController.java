package com.example.shopdemo.controller;

import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import com.example.shopdemo.service.AdminService;
import com.example.shopdemo.service.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    private final CashierService cashierService;

    @Autowired
    public AdminController(AdminService adminService, CashierService cashierService) {
        this.adminService = adminService;
        this.cashierService = cashierService;
    }

    @GetMapping("/cashiers")
    public List<Cashier> findAll(){
        return adminService.findAll();
    }

    @GetMapping("/cashiers/activeCashiers/{pageNo}")
    public List<Cashier> findAllActive(@PathVariable int pageNo){
        int pageSize = 2;
        Pageable paging = PageRequest.of(pageNo,pageSize);
        return adminService.findAllActive(paging);
    }

    @GetMapping("/cashiers/{cashierId}")
    public Cashier getCashier(@PathVariable int cashierId){
        Cashier theCashier = adminService.findById(cashierId);

        if (theCashier == null){
            throw new RuntimeException("Cashier id not found - " + cashierId);
        }

        return theCashier;
    }

    @PostMapping("/cashiers/addCashier")
    public Cashier addCashier(@RequestBody Cashier theCashier){

        theCashier.setId(0);

        adminService.save(theCashier);

        return theCashier;

    }

    @PutMapping("/cashiers/updateCashier")
    public Cashier updateCashier(@RequestBody Cashier theCashier){

        adminService.save(theCashier);

        return theCashier;
    }

    @PutMapping("/cashiers/tempDeleteCashier/{cashierId}")
    public Cashier tempDeleteCashier(@PathVariable int cashierId){

        Cashier theCashier = adminService.findById(cashierId);

        if (theCashier == null){
            throw new RuntimeException("Cashier id not found - " + cashierId);
        }

        theCashier.setStatus(0);

        adminService.save(theCashier);

        return theCashier;
    }

    @DeleteMapping("/cashiers/deleteCashier/{cashierId}")
    public String deleteCashier(@PathVariable int cashierId){

        Cashier tempCashier = adminService.findById(cashierId);

        // throw exception if null
        if (tempCashier == null){
            throw new RuntimeException("Cashier id not found - "+cashierId);
        }

        adminService.deleteById(cashierId);

        return "Deleted Cashier id - " + cashierId;
    }

    @GetMapping("/activeCustomers/{pageNo}")
    public List<Customer> findAllActiveCustomers(@PathVariable int pageNo){
        int pageSize = 2;
        Pageable paging = PageRequest.of(pageNo,pageSize);
        return cashierService.findAllActive(paging);
    }

    @PutMapping("/updateCustomer")
    public Customer updateCustomer(@RequestBody Customer theCustomer){

        cashierService.save(theCustomer);

        return theCustomer;
    }

}