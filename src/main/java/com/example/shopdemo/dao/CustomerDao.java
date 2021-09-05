package com.example.shopdemo.dao;

import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer,Integer> {

    // query and method to find active customers
    @Query(value = "SELECT * FROM customer where status = 1", nativeQuery = true)
    Page<Customer> findAllActive(Pageable pageable);

    Customer findByUsername(String username);

    // query and method to find user by phoneNumber
    @Query(value = "SELECT * FROM customer where phone_number = ?1", nativeQuery = true)
    Customer findByPhoneNumber(String phoneNumber);
}
