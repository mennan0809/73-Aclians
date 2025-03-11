package com.example.service;

import com.example.model.Order;
import com.example.model.User;
import com.example.model.Cart;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;


@Service
@SuppressWarnings("rawtypes")
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    public OrderService(OrderRepository orderRepository, UserService userService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public ArrayList<Order> getOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public void deleteOrderById ( @PathVariable UUID orderId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order != null) {
            orderRepository.deleteOrderById(orderId);
        } else {
            throw new IllegalArgumentException("Order not found");
        }
    }

}