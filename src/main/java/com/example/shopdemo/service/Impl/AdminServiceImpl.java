package com.example.shopdemo.service.Impl;

import com.example.shopdemo.dao.AdminDao;
import com.example.shopdemo.dao.CashierDao;
import com.example.shopdemo.dao.CustomerDao;
import com.example.shopdemo.model.Admin;
import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import com.example.shopdemo.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service(value = "adminService")
public class AdminServiceImpl implements AdminService, UserDetailsService{

    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminDao adminDao;
    private final CashierDao cashierDao;
    private final CustomerDao customerDao;
    private final BCryptPasswordEncoder passwordEncoder;
    String username;

    public AdminServiceImpl(AdminDao adminDao, CashierDao cashierDao, CustomerDao customerDao, BCryptPasswordEncoder passwordEncoder) {
        this.adminDao = adminDao;
        this.cashierDao = cashierDao;
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    // validating admin by username and password
    @Override
    public UserDetails loadUserByUsername(String adminId) throws UsernameNotFoundException {
        Admin admin = adminDao.findByUsername(adminId);
        if(admin == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        username = admin.getUsername();
        System.out.println(username);
        return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), getAuthority());
    }

    // give authority "ADMIN_ROLE"
    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List findAll() {
        return adminDao.findAll();
    }

    // method implementation for saving cashier
    @Override
    public Cashier saveCashier(Cashier theCashier) {

        // check if the cashier has a duplicate username
        Cashier cashierWithDuplicateUsername = cashierDao.findByUsername(theCashier.getUsername());
        if(cashierWithDuplicateUsername != null && theCashier.getId() != cashierWithDuplicateUsername.getId()) {
            log.error(String.format("Duplicate username %", theCashier.getUsername()));
            throw new RuntimeException("Duplicate username.");
        }

        // setting cashier status, addedBy, addedDate and encode password
        theCashier.setStatus(1);
        theCashier.setAddedBy(username);
        theCashier.setPassword(passwordEncoder.encode(theCashier.getPassword()));
        theCashier.setAddedDate(LocalDate.now());

        cashierDao.save(theCashier);
        return theCashier;
    }

    @Override
    public Cashier save(Cashier theCashier) {
        cashierDao.save(theCashier);
        return theCashier;
    }

    @Override
    public Customer save(Customer theCustomer) {
        customerDao.save(theCustomer);
        return theCustomer;
    }

    // method implementation for update cashier
    @Override
    public Cashier updateCashier(Cashier theCashier) {

        // check if the cashier has a duplicate username
        Cashier cashierWithDuplicateUsername = cashierDao.findByUsername(theCashier.getUsername());
        if(cashierWithDuplicateUsername != null && theCashier.getId() != cashierWithDuplicateUsername.getId()) {
            log.error(String.format("Duplicate username %", theCashier.getUsername()));
            throw new RuntimeException("Duplicate username.");
        }

        // setting cashier modifiedBy, modifiedDate and encode password
        theCashier.setModifiedBy(username);
        theCashier.setPassword(passwordEncoder.encode(theCashier.getPassword()));
        theCashier.setModifiedDate(LocalDate.now());
        System.out.println(theCashier);

        cashierDao.save(theCashier);

        return theCashier;
    }

    // method implementation for update customer
    @Override
    public Customer updateCustomer(Customer theCustomer) {

        // check if the customer has a duplicate username
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
        theCustomer.setModifiedBy(username);
        System.out.println(theCustomer.getModifiedBy());
        theCustomer.setPassword(passwordEncoder.encode(theCustomer.getPassword()));
        System.out.println(theCustomer.getPassword());
        theCustomer.setModifiedDate(LocalDate.now());


        customerDao.save(theCustomer);

        return theCustomer;
    }

}
