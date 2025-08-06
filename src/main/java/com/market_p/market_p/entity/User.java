package com.market_p.market_p.entity;

import jakarta.persistence.*;

@Table(name="User")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String address;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User(String username, String name, String password, String email, String phone, String address, Role role) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }
    public User() {}

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
