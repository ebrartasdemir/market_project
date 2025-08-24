package com.market_p.market_p.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String orderCode;
    private Status status=Status.PAID;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private Date orderDate;
    @ManyToOne
    @JoinColumn(name="adress_id")
    @JsonBackReference
    private Adress adress;
    private double totalPrice;
    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderItem> orderItems;


    public Order(String orderCode, List<OrderItem> orderItems, Status status, User user, Cart cart, Date orderDate, Adress adress) {
        this.orderCode = orderCode;
        this.status = status;
        this.user = user;
        this.cart = cart;
        this.orderDate = orderDate;
        this.adress = adress;
        this.orderItems = orderItems;
       this.totalPrice=orderItems.stream().mapToDouble(OrderItem::getPriceAtOrdersTime).sum();
       this.cart=user.getCart();
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String trackCode) {
        this.orderCode = trackCode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderPlacementTime) {
        this.orderDate = orderPlacementTime;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }
}
