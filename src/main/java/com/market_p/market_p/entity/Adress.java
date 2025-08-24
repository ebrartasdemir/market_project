package com.market_p.market_p.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "adresses")
public class Adress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String title;
    @NotBlank
    private String district;
    @NotBlank
    private String avunue;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotNull
    private int eno;
    @NotNull
    private int ino;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
    @OneToMany(mappedBy = "adress")
    @JsonManagedReference
    private List<Order> orders;

    public Adress(String title, String district, int eno, int ino, String avunue, String city, String street, User user, List<Order> orders) {
        this.title = title;
        this.district = district;
        this.avunue = avunue;
        this.city = city;
        this.street = street;
        this.user = user;
        this.eno = eno;
        this.ino = ino;
    }

    public Adress() {
    }

    public String getDistrict() {
        return district;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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

    public int getIno() {
        return ino;
    }

    public void setIno(int INO) {
        this.ino = INO;
    }

    public int getEno() {
        return eno;
    }

    public void setEno(int ENO) {
        this.eno = ENO;
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
