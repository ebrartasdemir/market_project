package com.market_p.market_p.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "adresses")
public class Adress {
    @Id
    private int id;
    private String district;
    private String avunue;
    private String city;
    private String street;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @OneToMany(mappedBy = "adress")
    @JsonManagedReference
    private List<Order> orders;

    public Adress(String district, String avunue, String city, String street, User user, List<Order> orders) {
        this.district = district;
        this.avunue = avunue;
        this.city = city;
        this.street = street;
        this.user = user;
        this.orders = orders;
    }

    public Adress() {
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAvunue() {
        return avunue;
    }

    public void setAvunue(String avunue) {
        this.avunue = avunue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
