package com.example.shopdemo.dao;

import com.example.shopdemo.model.Cashier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CashierDao extends JpaRepository<Cashier,Integer> {

    @Query(value = "SELECT * FROM cashier where status = 1", nativeQuery = true)
    List<Cashier> findAllActive(Pageable pageable);

    Cashier findByUsername(String username);

}
