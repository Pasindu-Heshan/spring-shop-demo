package com.example.shopdemo.service;

import com.example.shopdemo.model.Admin;
import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {

    public List<Cashier> findAll();

    public Cashier findById(int theId);

    public void save(Cashier theCashier);

    public void deleteById(int theId);

    public List<Cashier> findAllActive(Pageable pageable);



}
