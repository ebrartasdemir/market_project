package com.market_p.market_p.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Category name cannot be empty")
    @Size(max = 25,message = "Category name cannot be longer than 25 characters.")
    private String name;
    private String description;
    @OneToMany(mappedBy="category")
    @JsonManagedReference
    private List<Product> productLists;

    public Category() {}
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public  void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String categoryName) {
        this.name = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String categoryDescription) {
        this.description = categoryDescription;
    }

    public List<Product> getProductLists() {
        return productLists;
    }

    public void setProductLists(List<Product> productLists) {
        this.productLists = productLists;
    }
}
