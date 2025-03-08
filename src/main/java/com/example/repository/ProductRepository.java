package com.example.repository;

import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class ProductRepository extends MainRepository<Product> {
    public static List<Product> products = new ArrayList<>();
    public ProductRepository() {
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/products.json";
    }

    @Override
    protected Class<Product[]> getArrayType() {
        return Product[].class;
    }

    public Product addProduct(Product product){
        if(product==null){
            throw new NoSuchElementException("Product is null");
        }

        if(product.getName()==null||product.getName().isEmpty() ||product.getName().equals(" ")){
            throw new IllegalArgumentException("product name cannot be empty");
        }
        ArrayList<Product> products = findAll();
        for (Product p : products){
            if (p.getId().equals(product.getId())){
                throw new IllegalArgumentException("Product with this ID already exists");
            }
            if(p.getName().equals(product.getName())){
                throw new IllegalArgumentException("Product with this name already exists");
            }
        }
        products.add(product);
        overrideData(products);
        return product;
    };

    public ArrayList<Product> getProducts(){
        return findAll();
    };

    public Product getProductById(UUID productId){

        ArrayList<Product> products = findAll();
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    };
    public Product updateProduct(UUID productId, String newName, double newPrice){
        if(newName==null||newName.isEmpty() ||newName.equals(" ")){
            throw new IllegalArgumentException("product name cannot be empty");
        }
        ArrayList<Product> products = findAll();
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                product.setName(newName);
                product.setPrice(newPrice);
                overrideData(products);
                return product;
            }
        }
        throw new NoSuchElementException("Product with id " + productId + " not found");
    };
    public void applyDiscount(double discount, ArrayList<UUID> productIds){
        ArrayList<Product> products = findAll();
        for (UUID id : productIds) {
            if(getProductById(id) == null){
                throw new NoSuchElementException("Product with id " + id + " not found");
            }
        }
        for (Product product : products) {
            if (productIds.contains(product.getId())) {
                double discountedPrice = product.getPrice() * (1 - discount / 100);
                product.setPrice(discountedPrice);
            }
        }
        overrideData(products);
    };

    public void deleteProductById(UUID productId){
        ArrayList<Product> products = findAll();
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                products.remove(product);
                overrideData(products);
                saveAll(products);
                return;
            }
        }
        throw new NoSuchElementException("Product with ID " + productId + " not found");

    };

}
