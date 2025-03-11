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
    private final OrderService orderService;

    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
    }

    @PostMapping("/")
   public void addOrder ( @RequestBody Order order){
        orderService.addOrder(order);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable UUID orderId){
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/")
    public Iterable<Order> getAllOrders(){
        return orderService.getOrders();
    }

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
