package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public class CartRepository extends MainRepository<Cart> {
    public static List<Cart> carts = new ArrayList<>();

    public CartRepository() {
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/carts.json";
    }

    @Override
    protected Class<Cart[]> getArrayType() {
        return Cart[].class;
    }

    public Cart addCart(Cart cart) {
        ArrayList<Cart> carts = findAll();
        carts.add(cart);
        overrideData(carts); // Save updated data
        return cart;
    }

    public ArrayList<Cart> getCarts(){
        return findAll();
    }

    public Cart getCartById(UUID cartId){
        ArrayList<Cart> carts = findAll();
        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                return cart;
            }
        }
        return null;
    }

    public Cart getCartByUserId(UUID userId){
        ArrayList<Cart> carts = findAll();
        for (Cart cart : carts) {
            if (cart.getUserId().equals(userId)) {
                return cart;
            }
        }
        return null;
    }

    public void addProductToCart(UUID cartId, Product product){
        ArrayList<Cart> carts = findAll();
        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                cart.getProducts().add(product);
                overrideData(carts); // Save updated data
            }
        }
    }

    public void deleteProductFromCart(UUID cartId, Product product){
        ArrayList<Cart> carts = findAll();
        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                cart.getProducts().remove(product);
                overrideData(carts); // Save updated data
            }
        }
    }

    public void deleteCartById(UUID cartId){
        ArrayList<Cart> carts = findAll();
        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                carts.remove(cart);
                overrideData(carts); // Save updated data
                return;
            }
        }
    }

}
