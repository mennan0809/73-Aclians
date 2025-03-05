package com.example.controller;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.Order;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.example.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CartService cartService;
    private final ProductService productService;

    public UserController(UserService userService, CartService cartService, ProductService productService) {
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/")
    public ArrayList<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/{userId}/orders")
    public List<Order> getOrdersByUserId(@PathVariable UUID userId) {
        return userService.getOrdersByUserId(userId);
    }

    @PostMapping("/{userId}/checkout")
    public String addOrderToUser(@PathVariable UUID userId) {

        userService.addOrderToUser(userId);

        return "Order added successfully";  // Modified response to match test expectation
    }


    @PostMapping("/{userId}/removeOrder")
    public String removeOrderFromUser(@PathVariable UUID userId, @RequestParam UUID orderId) {
        userService.removeOrderFromUser(userId, orderId);
        return "Order removed successfully";
    }

    @DeleteMapping("/{userId}/emptyCart")
    public String emptyCart(@PathVariable UUID userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart == null) {
            return "Cart not found!";
        }
        cartService.deleteCartById(cart.getId());
        return "Cart emptied successfully";
    }


    @PutMapping("/addProductToCart")
    public String addProductToCart(@RequestParam UUID userId, @RequestParam UUID productId) {
        Product product = productService.getProductById(productId);
        Cart cart=cartService.getCartByUserId(userId);

        if (cart == null || cart.getProducts() == null || cart.getProducts().isEmpty()) {
            Cart cart1=new Cart();
            cart1.setUserId(userId);
            cartService.addCart(cart1);
            cartService.addProductToCart(cart1.getId(), product);
            return "Product added to cart";
        } else {
            System.out.println("Cart has items");
        }
        if(product != null) {
            cartService.addProductToCart(cart.getId(), product);
            return "Product added to cart";
        }
        else{
            return "Product doesn't Exist!";
        }

    }

    @PutMapping("/deleteProductFromCart")
    public String deleteProductFromCart(@RequestParam UUID userId, @RequestParam UUID productId) {
        Product product = productService.getProductById(productId);
        Cart cart=cartService.getCartByUserId(userId);

        if (cart == null || cart.getProducts() == null || cart.getProducts().isEmpty()) {
            return "Cart is empty";
        } else {
            System.out.println("Cart has items");
        }

        if(product != null) {
            cartService.deleteProductFromCart(cart.getId(), product);
            return "Product deleted from cart";
        }
        else{
            return "Product doesn't Exist";
        }

    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUserById(@PathVariable UUID userId) {
        if(getUserById(userId) == null) {
            return "User not found";
        }else {
            userService.deleteUserById(userId);
            return "User deleted successfully";
        }
    }
}
