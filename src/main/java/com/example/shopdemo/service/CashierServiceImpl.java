package com.example.shopdemo.service;

import com.example.shopdemo.dao.CashierDao;
import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CashierServiceImpl implements CashierService{

    private CashierDao cashierDao;

    @Autowired
    public CashierServiceImpl(CashierDao theCashierDao){
        cashierDao = theCashierDao;
    }

    @Override
    public Customer findById(int theId) {
        Optional<Customer> result = cashierDao.findById(theId);

        Customer theCustomer = null;

        if(result.isPresent()){
            theCustomer = result.get();
        }
        else {
            throw new RuntimeException("Did not find the customer id - " + theId);
        }

        return theCustomer;
    }



    @Override
    public void save(Customer theCustomer) {
        cashierDao.save(theCustomer);
    }

    @Override
    public List<Customer> findAll() {
        return cashierDao.findAll();
    }

    @Override
    public List<Customer> findAllActive(Pageable pageable) {
        List<Customer> pagedResult = cashierDao.findAllActive(pageable);

        return pagedResult;
    }
}
