package com.example.shopdemo.model;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_number")
    private int orderNumber;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "item_count")
    private int itemCount;

    @Column(name = "date")
    private Date date;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;*/

    public Order() {
    }

    public Order(int orderNumber, int customerId, int itemCount, Date date) {
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.itemCount = itemCount;
        this.date = date;
    }

    /*public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }*/

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", customerId=" + customerId +
                ", itemCount=" + itemCount +
                ", date=" + date +
                '}';
    }
}
