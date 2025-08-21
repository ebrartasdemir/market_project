package com.market_p.market_p.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private double priceAtOrdersTime;
    private int quantity;

    public OrderItem(Product product, Order order, double priceAtOrdersTime,int quantity) {
        this.product = product;
        this.order = order;
        this.priceAtOrdersTime = priceAtOrdersTime;
        this.quantity = quantity;
    }

    public OrderItem() {
    }

    public double getPriceAtOrdersTime() {
        return priceAtOrdersTime;
    }

    public void setPriceAtOrdersTime(double priceAtOrdersTime) {
        this.priceAtOrdersTime = priceAtOrdersTime;
    }

    public Order getOrder() {
        return order;
    }

    public int getId() {
        return id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
