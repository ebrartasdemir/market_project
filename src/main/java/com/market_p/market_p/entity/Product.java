package com.market_p.market_p.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message="Product name cannot be empty")
    @Size(max = 25,message = "Product name cannot be longer than 25 characters.")
    private String name;
    @Lob
    @Size(max=1000,message = "Product description cannot be longer than 1000 characters")
    private String description;
    @Min(0)
    private double price;//?
    @Min(0)
    private int quantity;//?
    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonBackReference
    private Category category;

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
