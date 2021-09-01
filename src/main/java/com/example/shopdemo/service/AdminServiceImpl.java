package com.example.shopdemo.service;

import com.example.shopdemo.dao.AdminDao;
import com.example.shopdemo.model.Admin;
import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{

    private AdminDao adminDao;

    @Autowired
    public AdminServiceImpl(AdminDao theAdminDao){
        adminDao = theAdminDao;
    }

    @Override
    public List<Cashier> findAll() {
        return adminDao.findAll();
    }

    @Override
    public Cashier findById(int theId) {
        Optional<Cashier> result = adminDao.findById(theId);

        Cashier theCashier = null;

        if(result.isPresent()){
            theCashier = result.get();
        }
        else {
            throw new RuntimeException("Did not find the cashier id - " + theId);
        }

        return theCashier;
    }

    @Override
    public void save(Cashier theCashier) {
        adminDao.save(theCashier);
    }

    @Override
    public void deleteById(int theId) {
        adminDao.deleteById(theId);
    }


    @Override
    public List<Cashier> findAllActive(Pageable pageable) {

        List<Cashier> pagedResult = adminDao.findAllActive(pageable);

        return pagedResult;
    }


}
