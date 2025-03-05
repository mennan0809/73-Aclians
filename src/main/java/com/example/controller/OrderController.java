package com.example.controller;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    //The Dependency Injection Variables
    private final OrderService orderService;

    //The Constructor with the requried variables mapping the Dependency Injection.
    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
    }

    // 8.4.2.1 Add Order
    @PostMapping("/")
   public void addOrder ( @RequestBody Order order){
        orderService.addOrder(order);
    }

    // 8.4.2.2 Get a Specific Order
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable UUID orderId){
        return orderService.getOrderById(orderId);
    }

    // 8.4.2.3 Get All Orders
    @GetMapping("/")
    public Iterable<Order> getAllOrders(){
        return orderService.getOrders();
    }

    // 8.4.2.4 Delete a Specific Order
    @DeleteMapping("/delete/{orderId}")
    public String deleteOrderById(@PathVariable UUID orderId){
        try {
            orderService.deleteOrderById(orderId);
            return "Order deleted successfully";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

}
