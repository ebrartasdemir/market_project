package com.market_p.market_p.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "Carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    @NotNull()
    @JsonBackReference
    private User user;
    private double totalPrice;
    @OneToMany(mappedBy="cart")
    @JsonManagedReference
    private List<CartItem> cartItems;
    @OneToMany(mappedBy = "cart")
    @JsonManagedReference
    private List<Order> orders;

    public Cart(User user, List<CartItem> cartItems, List<Order> orders) {
        this.user = user;
        this.cartItems = cartItems;
        this.totalPrice = cartItems.stream().mapToDouble(CartItem::getPrice).sum();
        this.orders = orders;
    }
    public Cart(User user) {
        this.user = user;
    }

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
