package com.market_p.market_p.dto;

public class ProductReqDto {
    private String name;
    private String description;
    private double price=-1;
    private int quantity=-1;
    private int categoryId;

    public ProductReqDto() {
    }
    public ProductReqDto(int categoryId, int quantity, double price, String description, String name) {
        this.categoryId = categoryId;
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

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
