package com.example.model;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Component
public class Order {
    private UUID id;
    private UUID userId;
    private double totalPrice;
    private List<Product> products=new ArrayList<>();

    // Constructors
    public Order(UUID userId, double totalPrice, List<Product> products) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.products = products;
    }
    public Order () {
        this.id = UUID.randomUUID();
    }

    // Kol El Getters
    public UUID getId() {
        return id;
    }
    public UUID getUserId() {
        return userId;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public List<Product> getProducts() {
        return products;
    }
    // Kol El Setters
    public void setId(UUID id) {
        this.id = id;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }

}