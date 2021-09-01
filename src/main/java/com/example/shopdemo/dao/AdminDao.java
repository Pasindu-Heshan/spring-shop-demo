package com.example.shopdemo.dao;

import com.example.shopdemo.model.Admin;
import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminDao extends JpaRepository<Cashier,Integer> {

    @Query(value = "SELECT * FROM cashier where status = 1", nativeQuery = true)
    List<Cashier> findAllActive(Pageable pageable);

}
