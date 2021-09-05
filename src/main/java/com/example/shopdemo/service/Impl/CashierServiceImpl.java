package com.example.shopdemo.service.Impl;

import com.example.shopdemo.dao.CashierDao;
import com.example.shopdemo.dao.CustomerDao;
import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import com.example.shopdemo.service.CashierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service(value = "cashierService")
public class CashierServiceImpl implements CashierService, UserDetailsService {

    private CashierDao cashierDao;
    private CustomerDao customerDao;
    private static final Logger log = LoggerFactory.getLogger(CashierServiceImpl.class);
    private final BCryptPasswordEncoder passwordEncoder;
    String username;

    public CashierServiceImpl(CashierDao theCashierDao, CustomerDao customerDao, BCryptPasswordEncoder passwordEncoder){
        cashierDao = theCashierDao;
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    // validating cashier by username and password
    @Override
    public UserDetails loadUserByUsername(String cashierId) throws UsernameNotFoundException {
        Cashier cashier = cashierDao.findByUsername(cashierId);
        if(cashier == null || cashier.getStatus()==0){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        username = cashier.getUsername();
        return new org.springframework.security.core.userdetails.User(cashier.getUsername(), cashier.getPassword(), getAuthority());

    }

    // give authority "ADMIN_CASHIER"
    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_CASHIER"));
    }



    @Override
    public List<Cashier> findAll() {
        return cashierDao.findAll();
    }

    @Override
    public Cashier findById(int theId) {
        Optional<Cashier> result = cashierDao.findById(theId);

        Cashier theCashier = null;

        if(result.isPresent()){
            theCashier = result.get();
        }
        else {
            throw new RuntimeException("Did not find the cashier id - " + theId);
        }

        return theCashier;
    }

    // method implementation for saving customer
    @Override
    public Customer saveCustomer(Customer theCustomer) {

        // check if the customer has a duplicate username
        System.out.println(theCustomer);
        Customer customerWithDuplicateUsername = customerDao.findByUsername(theCustomer.getUsername());
        Customer customerWithDuplicatePhoneNumber = customerDao.findByPhoneNumber(theCustomer.getPhoneNumber());
        if(customerWithDuplicateUsername != null && theCustomer.getId() != customerWithDuplicateUsername.getId()) {
            log.error(String.format("Duplicate username %", theCustomer.getUsername()));
            throw new RuntimeException("Duplicate username.");
        }else if (customerWithDuplicatePhoneNumber != null){
            log.error(String.format("Duplicate phoneNumber %", theCustomer.getPhoneNumber()));
            throw new RuntimeException("Duplicate phoneNumber.");
        }

        // setting customer modifiedBy, modifiedDate and encode password
        theCustomer.setStatus(1);
        theCustomer.setAddedBy(username);
        theCustomer.setPassword(passwordEncoder.encode(theCustomer.getPassword()));
        theCustomer.setAddedDate(LocalDate.now());
        System.out.println(theCustomer);
        customerDao.save(theCustomer);
        return theCustomer;
    }


    @Override
    public Customer save(Customer theCustomer) {
        customerDao.save(theCustomer);
        return theCustomer;
    }



    @Override
    public Cashier deleteById(int theId) {
        cashierDao.deleteById(theId);
        return null;
    }


    @Override
    public Page<Cashier> findAllActive(Pageable pageable) {

        Page<Cashier> pagedResult = cashierDao.findAllActive(pageable);

        return pagedResult;
    }



}
