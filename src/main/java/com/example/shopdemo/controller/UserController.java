package com.example.shopdemo.controller;

import com.example.shopdemo.dto.ApiResponse;
import com.example.shopdemo.dto.UserDto;
import com.example.shopdemo.service.AuthenticationFacadeService;
import com.example.shopdemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static final String SUCCESS = "success";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    private final UserService userService;

    private final AuthenticationFacadeService authenticationFacadeService;

    public UserController(UserService userService, AuthenticationFacadeService authenticationFacadeService) {
        this.userService = userService;
        this.authenticationFacadeService = authenticationFacadeService;
    }

    @Secured({ROLE_ADMIN})
    @GetMapping
    public ApiResponse listUser(){
        log.info(String.format("received request to list user %s", authenticationFacadeService.getAuthentication().getPrincipal()));
        return new ApiResponse(HttpStatus.OK, SUCCESS, userService.findAll());
    }

    @Secured({ROLE_ADMIN})
    @PostMapping
    public ApiResponse create(@RequestBody UserDto user){
        log.info(String.format("received request to create user %s", authenticationFacadeService.getAuthentication().getPrincipal()));
        return new ApiResponse(HttpStatus.OK, SUCCESS, userService.save(user));
    }

    @Secured({ROLE_ADMIN, ROLE_USER})
    @GetMapping(value = "/{id}")
    public ApiResponse getUser(@PathVariable long id){
        log.info(String.format("received request to update user %s", authenticationFacadeService.getAuthentication().getPrincipal()));
        return new ApiResponse(HttpStatus.OK, SUCCESS, userService.findOne(id));
    }

    @Secured({ROLE_ADMIN})
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(value = "id") Long id){
        log.info(String.format("received request to delete user %s", authenticationFacadeService.getAuthentication().getPrincipal()));
        userService.delete(id);
    }



}
