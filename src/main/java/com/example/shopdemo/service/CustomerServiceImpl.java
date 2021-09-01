package com.example.shopdemo.service;

import com.example.shopdemo.dao.CashierDao;
import com.example.shopdemo.dao.CustomerDao;
import com.example.shopdemo.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao theCustomerDao){
        customerDao = theCustomerDao;
    }
    @Override
    public void save(Order theOrder) {
        customerDao.save(theOrder);
    }
}
