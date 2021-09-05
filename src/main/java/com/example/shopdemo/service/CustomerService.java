package com.example.shopdemo.service;

import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import com.example.shopdemo.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    public Customer findById(int theId);

    public List<Customer> findAll();

    Customer findByPhoneNumber(String phoneNumber);

    public Order saveOrder(Order theOrder);

    Page<Customer> findAllActive(Pageable pageable);

}
