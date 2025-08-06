package com.market_p.market_p.entity;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "Role")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(String name, String description, List<User> users) {
        this.name = name;
        this.description = description;
        this.users = users;
    }
    public Role() {}


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

}
