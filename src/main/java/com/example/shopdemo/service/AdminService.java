package com.example.shopdemo.service;

import com.example.shopdemo.model.Admin;
import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;

import java.util.List;

public interface AdminService {

    public List<Admin> findAll();

    public Cashier saveCashier(Cashier theCashier);

    public Cashier save(Cashier theCashier);

    public Customer save(Customer theCustomer);

    public Cashier updateCashier(Cashier theCashier);

    public Customer updateCustomer(Customer theCustomer);



}
