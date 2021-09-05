package com.example.shopdemo.service;

import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CashierService {

    public List<Cashier> findAll();

    public Cashier findById(int theId);

    public Customer saveCustomer(Customer theCustomer);

    public Customer save(Customer theCustomer);

    public Cashier deleteById(int theId);

    public Page<Cashier> findAllActive(Pageable pageable);



}
