package com.example.repository;

import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
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
        if(cart==null||cart.getId()==null||cart.getUserId()==null){
            throw new NullPointerException("cart or cartId is null");
        }
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
                overrideData(carts);
            }
        }
    }

    public void deleteProductFromCart(UUID cartId, Product product){
        ArrayList<Cart> carts = findAll();
        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                cart.getProducts().remove(product);
                overrideData(carts);
                return;
            }
        }
        throw new NoSuchElementException("product not found in the cart");
    }

    public void deleteCartById(UUID cartId) {
        ArrayList<Cart> carts = findAll();
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getId().equals(cartId)) {
                carts.remove(i);
                overrideData(carts);
                return;
            }
        }
        throw new NoSuchElementException("Cart with ID " + cartId + " not found");

    }


}
