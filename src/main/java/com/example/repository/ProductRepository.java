package com.example.repository;

import com.example.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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
        ArrayList<Product> products = findAll();
        products.add(product);
        overrideData(products); // Save updated data
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
        ArrayList<Product> products = findAll();
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                product.setName(newName);
                product.setPrice(newPrice);
                overrideData(products); // Save updated data
                return product;
            }
        }
        return null;
    };
    public void applyDiscount(double discount, ArrayList<UUID> productIds){
        ArrayList<Product> products = findAll();
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
                return;
            }
        }
    };

}
