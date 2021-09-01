package com.example.shopdemo.service.Impl;

import com.example.shopdemo.dao.CustomerDao;
import com.example.shopdemo.model.Customer;
import com.example.shopdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao theCustomerDao){
        customerDao = theCustomerDao;
    }

    @Override
    public Customer findById(int theId) {
        Optional<Customer> result = customerDao.findById(theId);

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
        customerDao.save(theCustomer);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public List<Customer> findAllActive(Pageable pageable) {
        List<Customer> pagedResult = customerDao.findAllActive(pageable);

        return pagedResult;
    }
}
