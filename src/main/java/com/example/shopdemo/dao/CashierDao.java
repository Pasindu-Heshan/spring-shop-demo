package com.example.shopdemo.dao;

import com.example.shopdemo.model.Admin;
import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CashierDao extends JpaRepository<Cashier,Integer> {

    // query and method to find active cashiers
    @Query(value = "SELECT * FROM cashier where status = 1", nativeQuery = true)
    Page<Cashier> findAllActive(Pageable pageable);

    Cashier findByUsername(String username);
}
