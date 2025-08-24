package com.market_p.market_p.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.market_p.market_p.example.constants.Messages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Optional;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = Messages.Product.CANT_BE_EMPTY)
    @JsonBackReference
    private Product product;
    private double price;
    private int quantity=1;
    @ManyToOne
    @JoinColumn(name="cart_id")
    @JsonBackReference
    private Cart cart;

    public CartItem(Product product, int quantity, Cart cart) {
        this.product = product;
        this.price = product.getPrice();
        this.quantity = quantity;
        this.cart = cart;
    }

    public CartItem() {
    }

    public int getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }
    public void decreaseQuantity() {
        this.quantity -= 1;
    }
    public void increaseQuantity() {
        this.quantity += 1;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
