package com.example.repository;

import com.example.model.Order;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import com.example.repository.MainRepository;

import java.util.List;
import java.util.UUID;
@Repository
@SuppressWarnings("rawtypes")

public class OrderRepository extends MainRepository<Order> {
    public OrderRepository() {
     super();
    }

    public ArrayList<Order> getOrders() {
        return findAll();
    }

    // 6.5.2.1 Add Order
    public void addOrder(Order order) {
        ArrayList<Order> allOrders = findAll();
        allOrders.add(order);
        saveAll(allOrders);
    }
    // 6.5.2.2 Get Order
    public Order getOrder(UUID id) {
        ArrayList<Order> allOrders = findAll();
        for (Order order : allOrders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }
    // 6.5.2.3 Get Order by ID
    public Order getOrderById(UUID id) {
        ArrayList<Order> allOrders = findAll();
        for (Order order : allOrders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }
    // 6.5.2.4 Delete Order by ID
    public void deleteOrderById(UUID id) {
        ArrayList<Order> allOrders = findAll();
        Order order = getOrderById(id);
        if (order != null) {
            allOrders.remove(order);
            saveAll(allOrders);
        }
    }


    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/orders.json";
    }

    @Override
    protected Class<Order[]> getArrayType() {
        return Order[].class;
    }

    public void removeOrder(Order order) {
        ArrayList<Order> allOrders = findAll();
        allOrders.remove(order);
        saveAll(allOrders);
    }


}


