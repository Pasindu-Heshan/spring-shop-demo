package com.example.shopdemo.dao;

import com.example.shopdemo.model.Admin;
import com.example.shopdemo.model.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<Admin,Integer> {

    // method to find user by username
    Admin findByUsername(String username);

}
