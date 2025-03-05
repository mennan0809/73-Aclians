package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class Cart {
    private UUID id;
    private UUID userId;
    private List<Product> products = new ArrayList<>();

    public Cart() {
            this.id = UUID.randomUUID();
        }

        public Cart(UUID userId, List<Product> products) {
            this.id = UUID.randomUUID();
            this.userId = userId;
            this.products = products;
        }

        public Cart(UUID id, UUID userId, List<Product> products) {
            this.id = id;
            this.userId = userId;
            this.products = products;
        }

        public UUID getId() {
            return id;
        }

        public UUID getUserId() {
            return userId;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public void setUserId(UUID userId) {
            this.userId = userId;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        @Override
        public String toString() {
            return "Cart{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", products=" + products +
                    '}';
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true; // Same reference
            if (obj == null || getClass() != obj.getClass()) return false; // Different class

            Cart cart = (Cart) obj;
            return Objects.equals(id, cart.id); // Compare IDs (assuming it's unique)
        }

}


