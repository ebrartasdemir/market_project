package com.market_p.market_p.dto.Product;

public class ProductResDto {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String categoryName;

    public ProductResDto() {
    }
    public ProductResDto(String categoryName, int quantity, double price, String description, String name) {
        this.categoryName = categoryName;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
