package com.example.shopdemo.service.Impl;

import com.example.shopdemo.dao.AdminDao;
import com.example.shopdemo.dao.CashierDao;
import com.example.shopdemo.dao.RoleDao;
import com.example.shopdemo.dto.CashierDto;
import com.example.shopdemo.model.Admin;
import com.example.shopdemo.model.Cashier;
import com.example.shopdemo.model.Role;
import com.example.shopdemo.model.RoleType;
import com.example.shopdemo.service.AdminService;
import com.example.shopdemo.service.CashierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service(value = "cashierService")
public class CashierServiceImpl implements CashierService, UserDetailsService, AdminService {

    private AdminDao adminDao;

    private CashierDao cashierDao;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final RoleDao roleDao;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CashierServiceImpl(CashierDao cashierDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder,AdminDao adminDao){
        this.cashierDao = cashierDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.adminDao = adminDao;
    }

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

    /*@Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Cashier cashier = cashierDao.findByUsername(userId);
        if(cashier == null){
            log.error("Invalid username or password.");
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<GrantedAuthority> grantedAuthorities = getAuthorities(cashier);


        return new org.springframework.security.core.userdetails.User(cashier.getUsername(), cashier.getPassword(), grantedAuthorities);

    }

    private Set<GrantedAuthority> getAuthorities(Cashier user) {
        Set<Role> roleByUserId = user.getRoles();
        final Set<GrantedAuthority> authorities = roleByUserId.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString().toUpperCase())).collect(Collectors.toSet());
        return authorities;
    }*/

    @Override
    public CashierDto save(CashierDto cashierDto) {
        Cashier cashierWithDuplicateUsername = cashierDao.findByUsername(cashierDto.getUsername());
        if(cashierWithDuplicateUsername != null && cashierDto.getId() != cashierWithDuplicateUsername.getId()) {
            log.error(String.format("Duplicate username %", cashierDto.getUsername()));
            throw new RuntimeException("Duplicate username.");
        }

        Cashier cashier = new Cashier();
        cashier.setName(cashierDto.getName());
        cashier.setStatus(cashierDto.getStatus());
        cashier.setUsername(cashierDto.getUsername());
        cashier.setPassword(passwordEncoder.encode(cashierDto.getPassword()));
        cashier.setAddedDate(cashierDto.getAddedDate());
        cashier.setAddedBy(cashierDto.getAddedBy());
        cashier.setModifiedDate(cashierDto.getModifiedDate());
        cashier.setModifiedBy(cashierDto.getModifiedBy());
        List<RoleType> roleTypes = new ArrayList<>();
        cashierDto.getRole().stream().map(role -> roleTypes.add(RoleType.valueOf(role)));
        cashier.setRoles(roleDao.find(cashierDto.getRole()));
        cashierDao.save(cashier);
        return cashierDto;
    }

    @Override
    public List<Cashier> findAll() {
        return cashierDao.findAll();
    }

    @Override
    public Cashier findById(int theId) {
        Optional<Cashier> result = cashierDao.findById(theId);

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
        cashierDao.save(theCashier);
    }

    @Override
    public void deleteById(int theId) {
        cashierDao.deleteById(theId);
    }


    @Override
    public List<Cashier> findAllActive(Pageable pageable) {

        List<Cashier> pagedResult = cashierDao.findAllActive(pageable);

        return pagedResult;
    }



}
