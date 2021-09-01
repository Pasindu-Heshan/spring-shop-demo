package com.example.shopdemo.model;

import com.example.shopdemo.dto.CashierDto;
import com.example.shopdemo.dto.UserDto;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "cashier")
public class Cashier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private int status;

    @Column(name = "added_date")
    private Date addedDate;

    @Column(name = "added_by")
    private String addedBy;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "User_ROLES",
            joinColumns =  @JoinColumn(name ="USER_ID"),inverseJoinColumns= @JoinColumn(name="ROLE_ID"))
    private Set<Role> roles;

    public Cashier() {
    }

    public Cashier(String name, String username, String password, int status, Date addedDate, String addedBy, Date modifiedDate, String modifiedBy) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.status = status;
        this.addedDate = addedDate;
        this.addedBy = addedBy;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public CashierDto toCashierDto(){
        CashierDto cashierDto = new CashierDto();
        cashierDto.setId(this.id);
        cashierDto.setName(this.name);
        cashierDto.setStatus(this.status);
        cashierDto.setAddedDate(this.addedDate);
        cashierDto.setAddedBy(this.addedBy);
        cashierDto.setModifiedDate(this.modifiedDate);
        cashierDto.setModifiedBy(this.modifiedBy);
        cashierDto.setUsername(this.username);
        cashierDto.setRole(this.roles.stream().map(role -> role.getName().toString()).collect(Collectors.toList()));
        return cashierDto;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", addedDate=" + addedDate +
                ", addedBy='" + addedBy + '\'' +
                ", modifiedDate=" + modifiedDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}

