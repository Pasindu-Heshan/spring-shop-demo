package com.example.shopdemo.service.Impl;

import com.example.shopdemo.dao.CustomerDao;
import com.example.shopdemo.dao.OrderDao;
import com.example.shopdemo.model.Customer;
import com.example.shopdemo.model.Order;
import com.example.shopdemo.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService, UserDetailsService{

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    String username;
    private CustomerDao customerDao;
    private OrderDao orderDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao theCustomerDao, OrderDao orderDao){
        customerDao = theCustomerDao;
        this.orderDao = orderDao;
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
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Customer findByPhoneNumber(String phoneNumber) {
        return customerDao.findByPhoneNumber(phoneNumber);
    }



    @Override
    public Page<Customer> findAllActive(Pageable pageable) {
        Page<Customer> pagedResult = customerDao.findAllActive(pageable);

        return pagedResult;
    }

    // validating customer by username and password
    @Override
    public UserDetails loadUserByUsername(String customerId) throws UsernameNotFoundException {
        Customer customer = customerDao.findByUsername(customerId);
        if(customer == null || customer.getStatus()==0){
            log.error(String.format("invalid username or password %", customer.getUsername()));
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        username = customer.getUsername();
        System.out.println(username);
        return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(), getAuthority());

    }

    // give authority "ADMIN_CUSTOMER"
    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    // method implementation for placing order
    @Override
    public Order saveOrder(Order theOrder) {
        System.out.println(theOrder);
        /*if(!Objects.equals(username, customer.getUsername())){
            log.error(String.format("invalid user %", customer.getUsername()));
            throw new RuntimeException("Invalid user!");
        }*/

        // set customer id and date for the order
        Customer customer = customerDao.findByUsername(username);
        theOrder.setCustomerId(customer.getId());
        theOrder.setDate(LocalDate.now());

        System.out.println(theOrder);
        orderDao.save(theOrder);

        return theOrder;
    }

}
