package com.example.shopdemo.service.Impl;

import com.example.shopdemo.dao.AdminDao;
import com.example.shopdemo.dao.CashierDao;
import com.example.shopdemo.dao.RoleDao;
import com.example.shopdemo.model.Admin;
import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Role;
import com.example.shopdemo.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service(value = "adminService")
public class AdminServiceImpl implements AdminService, UserDetailsService {

    private AdminDao adminDao;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final RoleDao roleDao;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder){
        this.adminDao = adminDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

   /* @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }*/

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Admin admin = adminDao.findByUsername(userId);
        if(admin == null){
            log.error("Invalid username or password.");
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<GrantedAuthority> grantedAuthorities = getAuthorities(admin);


        return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), grantedAuthorities);

    }

    private Set<GrantedAuthority> getAuthorities(Admin user) {
        Set<Role> roleByUserId = user.getRoles();
        final Set<GrantedAuthority> authorities = roleByUserId.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString().toUpperCase())).collect(Collectors.toSet());
        return authorities;
    }
}
