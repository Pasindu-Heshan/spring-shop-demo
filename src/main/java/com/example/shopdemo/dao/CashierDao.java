package com.example.shopdemo.dao;

import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CashierDao extends JpaRepository<Customer,Integer> {

    @Query(value = "SELECT * FROM customer where status = 1", nativeQuery = true)
    List<Customer> findAllActive(Pageable pageable);
}
