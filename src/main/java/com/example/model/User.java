package com.example.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class User {
    private UUID id;
    private String name;
    private List<Order> orders=new ArrayList<>();

    public User() {
        this.id = UUID.randomUUID();
        this.orders = new ArrayList<>();
    }
    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.orders = new ArrayList<>();
    }

    public User(UUID id, String name, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orders=" + orders +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id) && Objects.equals(this.name, user.name);
    }
}
