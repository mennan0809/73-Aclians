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
    //The Dependency Injection Variables
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    //The Constructor with the requried variables mapping the Dependency Injection.
    public OrderService(OrderRepository orderRepository, UserService userService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
    }
    //  7.5.2.1 Add Order
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    //  7.5.2.2 Get All Orders
    public ArrayList<Order> getOrders(){
        return orderRepository.findAll();
    }

    // 7.5.2.3 Get a specific order
    public Order getOrderById(UUID orderId) {
        return orderRepository.getOrderById(orderId);
    }

    // 7.5.2.4 Delete Order by ID
    public void deleteOrderById ( @PathVariable UUID orderId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order != null) {
            orderRepository.deleteOrderById(orderId);
        } else {
            throw new IllegalArgumentException("Order not found");
        }
    }

}







    /*
    // 7.2.2.4 Get the User’s Orders
    public List<Order> getOrdersByUserId(UUID userId) {
        List<Order> orders = new OrderRepository().findAll();
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId().equals(userId)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    // 7.2.2.5 Add an Order to the User
    public void addOrderToUser(UUID userId) {
        User user = new User();
        user.setId(userId);
        Cart cart = new Cart();
        cart.setProducts(new ArrayList<Product>());
        Order order = new Order(user.getId(), cart.getTotalPrice(), cart.getProducts());
        new OrderRepository().addOrder(order);
    }

    // 7.2.2.7 Remove Order of specfic user
    public void removeOrderFromUser (UUID userId, UUID orderId) {
        List<Order> orders = new OrderRepository().findAll();
        for (Order order : orders) {
            if (order.getUserId().equals(userId) && order.getId().equals(orderId)) {
                new OrderRepository().removeOrder(order);
            }
        }
    }


*/


