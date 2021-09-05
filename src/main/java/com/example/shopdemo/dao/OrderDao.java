package com.example.shopdemo.dao;

import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDao extends JpaRepository<Order,Integer> {

    /*@Query(value = "insert into orders (order_number,)", nativeQuery = true)
    Order  saveOrder();*/
}
