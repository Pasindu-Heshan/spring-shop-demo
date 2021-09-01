package com.example.shopdemo.service;

import com.example.shopdemo.dto.UserDto;
import com.example.shopdemo.model.User;

import java.util.List;

public interface UserService {

    UserDto save(UserDto user);
    List<UserDto> findAll();
    User findOne(long id);
    void delete(long id);
}
