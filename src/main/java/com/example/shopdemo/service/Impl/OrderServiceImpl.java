package com.example.shopdemo.service.Impl;

import com.example.shopdemo.dao.OrderDao;
import com.example.shopdemo.model.Order;
import com.example.shopdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao theOrderDao){
        orderDao = theOrderDao;
    }
    @Override
    public void save(Order theOrder) {
        orderDao.save(theOrder);
    }
}
