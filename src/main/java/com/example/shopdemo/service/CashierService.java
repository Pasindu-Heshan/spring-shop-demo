package com.example.shopdemo.service;

import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CashierService {

    public Customer findById(int theId);

    public void save(Customer theCustomer);

    public List<Customer> findAll();

    List<Customer> findAllActive(Pageable pageable);

}
