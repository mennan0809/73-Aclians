package com.example.repository;

import com.example.model.Order;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import com.example.repository.MainRepository;

import java.util.List;
import java.util.NoSuchElementException;
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

    public void addOrder(Order order) {
        if (order == null) {
            throw new NullPointerException("Order is null");
        }

        if (getOrderById(order.getId()) != null) {
            throw new IllegalArgumentException("Order with this ID already exists");
        }
        ArrayList<Order> allOrders = findAll();
        allOrders.add(order);
        saveAll(allOrders);
    }
    public Order getOrder(UUID id) {
        ArrayList<Order> allOrders = findAll();
        for (Order order : allOrders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }
    public Order getOrderById(UUID id) {
        ArrayList<Order> allOrders = findAll();
        for (Order order : allOrders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }
    public void deleteOrderById(UUID id) {
        ArrayList<Order> allOrders = findAll();
        Order order = getOrderById(id);
        if (order != null) {
            allOrders.remove(order);
            saveAll(allOrders);
            return;
        }
        else{
            throw new NoSuchElementException("Order with id " + id + " not found");
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
        if(getOrderById(order.getId()) != null) {
            ArrayList<Order> allOrders = findAll();
            allOrders.remove(order);
            saveAll(allOrders);
            return;
        }
        else{
            throw new NoSuchElementException("Order with id " + order.getId() + " not found");
        }
    }


}


