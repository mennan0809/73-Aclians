package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    };

    @GetMapping("/")
    public ArrayList<Product> getProducts(){
        return productService.getProducts();
    };

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable UUID productId){
        return productService.getProductById(productId);
    };

    @PutMapping("/update/{productId}")
    public Product updateProduct(@PathVariable UUID productId, @RequestBody Map<String,Object>
            body){
        String newName = (String) body.get("name");
        Double newPrice = body.get("price") != null ? ((Number) body.get("price")).doubleValue() : null;

        return productService.updateProduct(productId,newName,newPrice);
    };

    @PutMapping("/applyDiscount")
    public String applyDiscount(@RequestParam double discount,@RequestBody ArrayList<UUID>
            productIds){
        if (discount <= 0 || discount > 100) {
            return "Invalid discount percentage. It must be between 0 and 100.";
        }

        if (productIds == null || productIds.isEmpty()) {
            return "No product IDs provided.";
        }

        // Apply discount
        productService.applyDiscount(discount, productIds);

        return "Discount of " + discount + "% applied to selected products.";
    };

    @DeleteMapping("/delete/{productId}")
    public String deleteProductById(@PathVariable UUID productId){
        Product product = productService.getProductById(productId);

        if (product == null) {
            return "Product with ID " + productId + " not found.";
        }

        // Perform deletion
        productService.deleteProductById(productId);

        return "Product with ID " + productId + " has been deleted successfully.";
    };

}
