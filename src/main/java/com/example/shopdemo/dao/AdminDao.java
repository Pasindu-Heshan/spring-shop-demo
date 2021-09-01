package com.example.shopdemo.dao;

import com.example.shopdemo.model.Admin;
import com.example.shopdemo.model.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<Admin,Integer> {

    Admin findByUsername(String username);
}
