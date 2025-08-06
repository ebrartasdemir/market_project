package com.market_p.market_p.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    public int getId() {
        return id;
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
