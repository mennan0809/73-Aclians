package com.example.service;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.Order;
import com.example.repository.CartRepository;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;

    public UserService(UserRepository userRepository, CartRepository cartRepository, CartService cartService) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    public ArrayList<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUserById(UUID userId) {
        return userRepository.getUserById(userId);
    }

    public List<Order> getOrdersByUserId(UUID userId) {
        return userRepository.getOrdersByUserId(userId);
    }

    public void addOrderToUser(UUID userId) {
        Cart cart = cartService.getCartByUserId(userId);

        if (cart == null || cart.getProducts().isEmpty()) {
            return;
        }
        System.out.println(cart);
        double price = 0.0;
        for (Product product : cart.getProducts()) {
            price += product.getPrice();
        }

        Order newOrder = new Order(userId, price, cart.getProducts());

        userRepository.addOrderToUser(userId, newOrder);

        emptyCart(userId);
    }

    public void emptyCart(UUID userId) {
        Cart cart = cartService.getCartByUserId(userId);
        for(Product product : cart.getProducts()) {
            cartService.deleteProductFromCart(cart.getId(), product);
        }
    }


    public void removeOrderFromUser(UUID userId, UUID orderId) {
        userRepository.removeOrderFromUser(userId, orderId);
    }

    public void deleteUserById(UUID userId) {
        userRepository.deleteUserById(userId);
    }
}
