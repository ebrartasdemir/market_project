package com.market_p.market_p.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.market_p.market_p.example.constants.Messages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message= Messages.Product.INVALID_BLANK_NAME)
    @Size(max = 25,message = Messages.Product.INVALID_MAX_CHARACTERS_LENGTH_NAME)
    private String name;
    @Lob
    @Size(max=1000,message =Messages.Product.INVALID_MAX_CHARACTERS_LENGTH_DESCRIPTION )
    private String description;
    @Min(value = 0,message = Messages.Product.INVALID_PRICE_CANT_BE_NEGATIVE)
    private double price;
    @Min(value = 0,message =  Messages.Product.INVALID_QUANTITY_CANT_BE_NEGATIVE)
    private int quantity;
    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonBackReference
    private Category category;
    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<CartItem> cartItems;
    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public Product() {}
    public Product(Category category, int quantity, double price, String description, String name) {
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Product(Category category, String name) {
        this.category = category;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
