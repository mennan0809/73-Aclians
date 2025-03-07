package com.example.repository;

import com.example.model.Cart;
import com.example.model.User;
import com.example.model.Order;
import com.example.service.CartService;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class UserRepository extends MainRepository<User> {
    public static List<User> users = new ArrayList<>();
    private final CartService cartService;

    public UserRepository(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/users.json";
    }

    @Override
    protected Class<User[]> getArrayType() {
        return User[].class;
    }

    public ArrayList<User> getUsers() {
        return findAll();
    }

    public User getUserById(UUID userId) {
        ArrayList<User> users = findAll();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public User addUser(User user) {
        ArrayList<User> users = findAll();
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                throw new NoSuchElementException("User with ID " + user.getId() + " already exists");
            }
        }
        save(user);
        return user;
    }

    public List<Order> getOrdersByUserId(UUID userId) {
        User user = getUserById(userId);
        if(user == null){
            return null;
        } else{
            return user.getOrders();
        }
    }

    public void addOrderToUser(UUID userId, Order order) {
        ArrayList<User> users = findAll();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                List<Order> orders = user.getOrders();
                orders.add(order);
                user.setOrders(orders);
                save(user);
                overrideData(users);
                saveAll(users);
                return;
            }
        }
    }

    public void removeOrderFromUser(UUID userId, UUID orderId) {
        ArrayList<User> users = findAll();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                for(Order order : user.getOrders()){
                    if(order.getId().equals(orderId)){
                        user.getOrders().remove(order);
                        overrideData(users);
                        return;
                    }
                }
            }
        }
        throw new NoSuchElementException("Order with id " + orderId + " not found");
    }

    public void deleteUserById(UUID userId) {
        ArrayList<User> users = findAll();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                users.remove(user);
                overrideData(users);
                return;
            }
        }
        throw new NoSuchElementException("User with id " + userId + " not found");
    }
}
